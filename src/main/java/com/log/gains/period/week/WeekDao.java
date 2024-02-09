package com.log.gains.period.week;

import org.springframework.stereotype.Repository;

@Repository
public class WeekDao {
    private final WeekRepository weekRepository;

    public WeekDao(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    public void saveWeek (Week week) {
        weekRepository.save(week);
    }
}
