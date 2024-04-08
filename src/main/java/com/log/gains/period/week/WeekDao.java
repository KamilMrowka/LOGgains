package com.log.gains.period.week;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class WeekDao {
    private final WeekRepository weekRepository;

    public WeekDao(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    public void saveWeek (Week week) {
        weekRepository.save(week);
    }

    public Optional<Week> getWeekByFirstDay (LocalDate firstDay) {
        return weekRepository.getWeekByFirstDay(firstDay);
    }
}
