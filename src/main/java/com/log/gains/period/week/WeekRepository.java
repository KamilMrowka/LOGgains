package com.log.gains.period.week;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {

    Optional<Week> getWeekByFirstDay(LocalDate firstDay);

}
