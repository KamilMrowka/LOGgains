package com.log.gains.day;

import com.log.gains.jwt.JwtService;
import com.log.gains.exception.*;
import com.log.gains.user.UserRepository;
import com.log.gains.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DayService {
    private final DayDAO dayDAO;
    private UserService userService;

    public DayService(@Qualifier("database") DayDAO dayDAO, JwtService jwtService, UserRepository userRepository, UserService userService) {
        this.dayDAO = dayDAO;
        this.userService = userService;
    }


    public void createDay (RequestNewDayDTO dayDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Day day = new Day();
        LocalDate date = null;

        //If date provided, check format
        if (dayDTO.date() != null) {
            try {
                date = LocalDate.parse(dayDTO.date());
            } catch (Exception e) {
                throw new UnacceptedDateFormatException(
                        "No date provided or provided date has wrong format. F: 'YEAR-MM-DD'"
                );
            }
        }

        //Two day entities with same date are not allowed TODO: (for the same user)
        if (dayDTO.date() == null) {
            if (dayDAO.existsDayByUserIDAndDate(LocalDate.now(), userService.getIdByUsername(currentUsername))) {
                throw new DayAlreadyExistsException("Day already exists. Failed to create.");
            }
        } else {
            if (dayDAO.existsDayByUserIDAndDate(date, userService.getIdByUsername(currentUsername))) {
                System.out.println(dayDTO.date());
                throw new DayAlreadyExistsException("Day already exists. Failed to create.");
            }
        }

        if (dayDTO.weightMeasurement() != null) {
            day.setWeightMeasurement(dayDTO.weightMeasurement());
        }

        if (dayDTO.caloriesConsumed() != null) {
            day.setCaloriesConsumed(dayDTO.caloriesConsumed());
        }
        if (dayDTO.date() == null) {
            day.setDataDate(LocalDate.now());
        } else {
            day.setDataDate(LocalDate.parse(dayDTO.date()));
        }
        day.setUserId(userService.getIdByUsername(currentUsername));
        dayDAO.createDay(day);
    }

    public void deleteExistingDay(String date) {
        LocalDate checkedDate;
        try {
            checkedDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new UnacceptedDateFormatException(
                    "Provided date has wrong format. F: 'YEAR-MM-DD'"
            );
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Day day = dayDAO.getUsersDayByDate(checkedDate, userService.getIdByUsername(currentUsername))
                .orElseThrow(() -> new DayDoesNotExistException("Day has no data to delete"));
        dayDAO.deleteUsersDay(day);
    }

    public List<Day> getUsersDays () {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return dayDAO.getAllUsersDays(userService.getIdByUsername(currentUsername));
    }

    public void updateUsersDay (RequestedDayUpdateDTO updateDTO) {

        LocalDate date = null;

        //Date is required to update a day
        if (updateDTO.date() == null) {
            throw new RequestHasNoDateException("Cannot update without provided date");
        } else {
            try {
                date = LocalDate.parse(updateDTO.date());
            } catch (Exception e) {
                throw new UnacceptedDateFormatException(
                        "No date provided or provided date has wrong format. F: 'YEAR-MM-DD'"
                );
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();


        Day beingUpdated = dayDAO.getUsersDayByDate(date, userService.getIdByUsername(currentUsername))
                .orElseThrow(() -> new DayDoesNotExistException(
                        "Cannot update non existing day"
                ));

        boolean hasChanges = false;
        if (updateDTO.caloriesConsumed() != null &&
                updateDTO.caloriesConsumed() != beingUpdated.getCaloriesConsumed()) {
            beingUpdated.setCaloriesConsumed(updateDTO.caloriesConsumed());
            hasChanges = true;
        }
        if (updateDTO.weightMeasurement() != null &&
                updateDTO.weightMeasurement() != beingUpdated.getWeightMeasurement()) {
            beingUpdated.setWeightMeasurement(updateDTO.weightMeasurement());
            hasChanges = true;
        }
        if (!hasChanges) {
            throw new NoApplicableChangesException("No changes to apply");
        }

        dayDAO.updateUsersDay(beingUpdated);
    }
}
