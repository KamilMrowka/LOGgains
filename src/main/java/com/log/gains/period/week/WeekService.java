package com.log.gains.period.week;

import com.log.gains.exception.UnacceptedDateFormatException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
public class WeekService {

    private final WeekDao weekDao;

    private LocalDate getFirstDay (LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public WeekService(WeekDao weekDao) {
        this.weekDao = weekDao;
    }

    private Long createWeek (LocalDate includedDay) {
        LocalDate firstDay = getFirstDay(includedDay);
        LocalDate lastDay = includedDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        Week week = new Week(firstDay, lastDay);
        weekDao.saveWeek(week);
        return week.getId();
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

    public Week getWeek (String dateString) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new UnacceptedDateFormatException("Provided date has a wrong format. F: YYYY-MM-DD");
        }
        LocalDate firstDay = getFirstDay(date);
        return weekDao.getWeekByFirstDay(firstDay).orElseThrow();
    }
}
