package com.log.gains.period.month;

import org.springframework.stereotype.Repository;

@Repository
public class MonthDao {
    private final MonthRepository repository;

    public MonthDao(MonthRepository repository) {
        this.repository = repository;
    }

    public void saveMonth (Month month) {
        repository.save(month);
    }
}
