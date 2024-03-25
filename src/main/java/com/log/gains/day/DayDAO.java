package com.log.gains.day;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayDAO{
    void saveDay(Day day);
    List<Day> getAllUsersDays(Long userId);
    void updateUsersDay(Day day);

    void deleteUsersDay(Day day);

    Optional<Day> getUsersDayByDate(LocalDate date, Long userID);

    boolean existsDayByUserIDAndDate(LocalDate date, Long userID);

    List<Day> findDaysByWeekIdAndUserId (Long weekId, Long userId);

    List<Day> findDaysByMonthIdAndUserId (Long monthId, Long userId);

}
