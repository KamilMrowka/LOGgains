package com.log.gains.day;

import com.log.gains.date.DateService;
import com.log.gains.exception.*;
import com.log.gains.period.PeriodService;
import com.log.gains.period.month.MonthService;
import com.log.gains.period.week.WeekService;
import com.log.gains.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DayService {
    private final DayDAO dayDAO;
    private final UserService userService;
    private final WeekService weekService;
    private final MonthService monthservice;
    private final DateService dateService;

    public DayService(@Qualifier("database") DayDAO dayDAO, UserService userService, WeekService weekService, MonthService monthService, PeriodService periodService, DateService dateService) {
        this.dayDAO = dayDAO;
        this.userService = userService;
        this.weekService = weekService;
        this.monthservice = monthService;
        this.dateService = dateService; 
    }


    public void createDay (RequestNewDayDTO dayDTO) {
        String currentUsername = getCurrentUser();
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

            if (dayDAO.existsDayByUserIDAndDate(date, userService.getIdByUsername(currentUsername))) {
                System.out.println(dayDTO.date());
                throw new DayAlreadyExistsException("Day already exists. Failed to create.");
            }
        } else {
            if (dayDAO.existsDayByUserIDAndDate(LocalDate.now(), userService.getIdByUsername(currentUsername))) {
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
            day.setDate(LocalDate.now());
        } else {
            day.setDate(LocalDate.parse(dayDTO.date()));
        }
        day.setWeekDay(day.getDate().getDayOfWeek().toString().substring(0, 3));
        day.setFDate(dateService.formatDate(day.getDate()));
        day.setUserId(userService.getIdByUsername(currentUsername));
        day.setWeekId(weekService.getCorrespondingWeekId(day.getDate()));
        day.setMonthId(monthservice.getCorrespondingMonthId(day.getDate()));
        dayDAO.saveDay(day);
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

        String currentUsername = getCurrentUser();

        Day day = dayDAO.getUsersDayByDate(checkedDate, userService.getIdByUsername(currentUsername))
                .orElseThrow(() -> new DayDoesNotExistException("Day has no data to delete"));
        dayDAO.deleteUsersDay(day);
    }

    public List<Day> getUsersDays () {
        String currentUsername = getCurrentUser();
        return dayDAO.getAllUsersDays(userService.getIdByUsername(currentUsername));
    }

    public void updateUsersDay (RequestedDayUpdateDTO updateDTO) {

        LocalDate date;

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

        String currentUsername = getCurrentUser();


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

    public Day getToday() {
        String currentUser = getCurrentUser();
        Optional<Day> todayOptional = dayDAO.findDayByDateAndUserId(LocalDate.now(), userService.getIdByUsername(currentUser));
        return todayOptional.orElseGet(Day::new);
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
