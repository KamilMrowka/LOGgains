package com.log.gains.period.day;

import java.util.List;
import java.util.Optional;

public interface DayDAO{
    void addDay(Day day);
    List<Day> getAllDays();
    void updateDay(Long id, Day day);
    Optional<Day> getDayById(Long id);
    void deleteDay(Long id);
}
