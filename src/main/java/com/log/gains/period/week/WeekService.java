package com.log.gains.period.week;

import com.log.gains.day.Day;
import com.log.gains.day.DayDAO;
import com.log.gains.day.DayRepository;
import com.log.gains.day.DayService;
import com.log.gains.exception.NotStagedDateException;
import com.log.gains.exception.UnacceptedDateFormatException;
import com.log.gains.period.PeriodService;
import com.log.gains.period.SortDayByDate;
import com.log.gains.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WeekService {

    private final WeekDao weekDao;
    private final DayDAO dayDAO;
    private final UserService userService;
    private final PeriodService periodService;


    public WeekService(WeekDao weekDao, PeriodService periodService, @Lazy DayService dayService, DayRepository dayRepository, DayDAO dayDAO, UserService userService, PeriodService periodService1) {
        this.weekDao = weekDao;
        this.dayDAO = dayDAO;
        this.userService = userService;
        this.periodService = periodService1;
    }

    public LocalDate getFirstDay (LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public Long getCorrespondingWeekId (LocalDate date) {
        Long correspondingWeekId;
        LocalDate firstDay = getFirstDay(date);
        Optional<Week> savedCorrespondingWeek = weekDao.getWeekByFirstDay(firstDay);
        if (savedCorrespondingWeek.isEmpty()) {
            correspondingWeekId = createWeek(date);
        } else {
            correspondingWeekId = savedCorrespondingWeek.get().getId();
        }
        return correspondingWeekId;
    }

    public Long getCorrespondingWeekIdIfExists (LocalDate date) {
        LocalDate firstDay = getFirstDay(date);
        Week week = weekDao.getWeekByFirstDay(firstDay).orElseThrow();
        return week.getId();
    }

    public ArrayList<String> getWeekDays(LocalDate firstDay) {
        ArrayList<String> weekDays = new ArrayList<>();
        LocalDate lastDay = firstDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        for (LocalDate day = firstDay; !day.equals(lastDay.plusDays(1)); day = day.plusDays(1)) {
            String unformattedDay = day.toString();
            String formattedDay = unformattedDay.split("-")[2] + "." + unformattedDay.split("-")[1];
            weekDays.add(formattedDay);
        }
        return weekDays;
    }

    public Week getWeek (LocalDate date) {
        LocalDate firstDay = getFirstDay(date);
        return weekDao.getWeekByFirstDay(firstDay).orElseThrow();
    }

    public double getUsersWeekMedianWeight (LocalDate date) {
        Long weekId = getCorrespondingWeekId(date);
        List<Day> weekDays = findUsersDaysByWeekId(weekId);
        return periodService.getMedianWeight(weekDays);
    }

    public ArrayList<Day> findUsersDaysByWeekId (Long weekId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ArrayList<Day> unsortedDays = dayDAO.findDaysByWeekIdAndUserId(weekId, userService.getIdByUsername(username));
        return sortByDate(unsortedDays);
    }

    private ArrayList<Day> sortByDate (ArrayList<Day> days) {
        Collections.sort(days, new SortDayByDate());
        return days;
    }

    private Long createWeek (LocalDate includedDay) {
        if (includedDay.isBefore(LocalDate.of(2023,1, 1)) || includedDay.isAfter(LocalDate.of(2100, 1, 1))) {
            throw new NotStagedDateException("Select a day from 2023 upwards and before the year of 2100");
        }
        LocalDate firstDay = getFirstDay(includedDay);
        LocalDate lastDay = includedDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        Week week = new Week(firstDay, lastDay);
        weekDao.saveWeek(week);
        return week.getId();
    }
}
