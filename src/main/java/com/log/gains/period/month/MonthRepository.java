package com.log.gains.period.month;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MonthRepository extends JpaRepository<Month, Long> {
    public Optional<Month> getMonthByFirstDay (LocalDate firstDay);
}
