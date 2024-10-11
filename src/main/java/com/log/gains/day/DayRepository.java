package com.log.gains.day;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    boolean existsDayByDate(LocalDate date);
    Optional<Day> getDayByDateAndUserId (LocalDate date, Long userId);

    boolean existsDayByDateAndUserId(LocalDate localDate, Long userID);

    ArrayList<Day> findAllByUserIdOrderByDate (Long userId);

    ArrayList<Day> findDaysByWeekIdAndUserId (Long weekId, Long userId);

    ArrayList<Day> findDaysByMonthIdAndUserIdOrderByDate (Long monthId, Long userId);

}
