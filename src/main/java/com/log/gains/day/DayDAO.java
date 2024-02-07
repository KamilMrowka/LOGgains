package com.log.gains.day;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayDAO{
    void createDay(Day day);
    List<Day> getAllUsersDays(Long userId);
    void updateUsersDay(Day day);

    void deleteUsersDay(Day day);

    Optional<Day> getUsersDayByDate(LocalDate date, Long userID);

    boolean existsDayByUserIDAndDate(LocalDate date, Long userID);

}
