package com.log.gains.day;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    boolean existsDayByDataDate(LocalDate date);

    Optional<Day> getDayByDataDateAndUserId (LocalDate date, Long userId);

    boolean existsDayByDataDateAndUserId (LocalDate localDate, Long userID);

    List<Day> findAllByUserId (Long userId);
}
