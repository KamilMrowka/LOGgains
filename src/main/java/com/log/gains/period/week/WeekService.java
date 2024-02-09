package com.log.gains.period.week;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class WeekService {

    private final WeekDao weekDao;

    public WeekService(WeekDao weekDao) {
        this.weekDao = weekDao;
    }

    public Long createCurrentWeek () {
        return createWeek(LocalDate.now());
    }

    public Long createWeek (LocalDate includedDay) {
        LocalDate firstDay = includedDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDay = includedDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        Week week = new Week(firstDay, lastDay);
        weekDao.saveWeek(week);
        return week.getId();
    }
}
