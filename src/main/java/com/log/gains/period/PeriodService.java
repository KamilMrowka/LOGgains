package com.log.gains.period;

import com.log.gains.day.Day;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class PeriodService {

    // TODO
    public List<Day> getPeriodDays (LocalDate date, Period type) {
        LocalDate firstDay;

        if (type.equals(Period.WEEK)) {
            firstDay = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        } else if (type.equals(Period.MONTH)) {
            firstDay = date.withDayOfMonth(1);
        }
        return new ArrayList<Day>();
    }
}
