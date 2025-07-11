package com.log.gains.day;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DayDAO{

    void saveDay(Day day);
    List<Day> getAllUsersDays(Long userId);
    void updateUsersDay(Day day);

    void deleteUsersDay(Day day);

    Optional<Day> getUsersDayByDate(LocalDate date, Long userID);

    boolean existsDayByUserIDAndDate(LocalDate date, Long userID);

    ArrayList<Day> findDaysByWeekIdAndUserId (Long weekId, Long userId);

    ArrayList<Day> findDaysByMonthIdAndUserId (Long monthId, Long userId);

    Optional<Day> findDayByDateAndUserId(LocalDate date, Long userId);

}
