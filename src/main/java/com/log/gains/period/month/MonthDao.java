package com.log.gains.period.month;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class MonthDao {
    private final MonthRepository repository;

    public MonthDao(MonthRepository repository) {
        this.repository = repository;
    }

    public void saveMonth (Month month) {
        repository.save(month);
    }

    public Optional<Month> getMonthByFirstDay (LocalDate firstDay) {
        return repository.getMonthByFirstDay(firstDay);
    }
}
