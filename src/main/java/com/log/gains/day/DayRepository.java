package com.log.gains.day;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    boolean existsDayByDate(LocalDate date);
    Optional<Day> getDayByDateAndUserId (LocalDate date, Long userId);

    boolean existsDayByDateAndUserId(LocalDate localDate, Long userID);

    List<Day> findAllByUserId (Long userId);
}
