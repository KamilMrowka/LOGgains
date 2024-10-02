package com.log.gains.period.month;

import com.log.gains.day.Day;
import com.log.gains.day.DayDAO;
import com.log.gains.day.DayService;
import com.log.gains.exception.NotStagedDateException;
import com.log.gains.period.PeriodService;
import com.log.gains.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonthService {

    private final MonthDao monthDao;
    private final PeriodService periodService;
    private final DayDAO dayDAO;
    private final UserService userService;

    public MonthService(MonthDao monthDao, PeriodService periodService, DayDAO dayDAO, UserService userService) {
        this.monthDao = monthDao;
        this.periodService = periodService;
        this.dayDAO = dayDAO;
        this.userService = userService;
    }

   public Long getCorrespondingMonthId (LocalDate date) {
        LocalDate firstDay = date.withDayOfMonth(1);
        Long correspondingMonthId;
        Optional<Month> savedMonth = monthDao.getMonthByFirstDay(firstDay);
        if (savedMonth.isEmpty()) {
            correspondingMonthId = createMonth(firstDay);
        } else {
            correspondingMonthId = savedMonth.get().getId();
        }
        return correspondingMonthId;
    }

    public Long getCorrespondingMonthIdIfExists(LocalDate date) {
        LocalDate firstDay = date.withDayOfMonth(1);
        Month savedMonth = monthDao.getMonthByFirstDay(firstDay).orElseThrow();
        return savedMonth.getId();
    }

    public double getUsersMonthMedianWeight (LocalDate date) {
        Long monthId = getCorrespondingMonthId(date);
        List<Day> monthDays = findUsersDaysByMonthId(monthId);
        return periodService.getMedianWeight(monthDays);
    }

    public ArrayList<Day> findUsersDaysByMonthId (Long monthId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return dayDAO.findDaysByMonthIdAndUserId(monthId, userService.getIdByUsername(username));
    }

    private Long createMonth (LocalDate includedDay) {
        if (includedDay.isBefore(LocalDate.of(2023,1, 1)) || includedDay.isAfter(LocalDate.of(2100, 1, 1))) {
            throw new NotStagedDateException("Select a day from 2023 upwards and before the year of 2100");
        }
        LocalDate firstDay = includedDay.withDayOfMonth(1);
        int lengthOfMonth = firstDay.lengthOfMonth();
        LocalDate lastDay = firstDay.plusDays(lengthOfMonth - 1);
        Month month = new Month(firstDay, lastDay);
        monthDao.saveMonth(month);
        return month.getId();
    }
}
