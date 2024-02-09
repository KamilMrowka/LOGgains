package com.log.gains.day;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("database")
public class DayDatabaseDataAccessService implements DayDAO{
    private final DayRepository dayRepository;

    public DayDatabaseDataAccessService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public void saveDay(Day day) {
        dayRepository.save(day);
    }

    @Override
    public List<Day> getAllUsersDays(Long id) {
        return dayRepository.findAllByUserId(id);
    }

    @Override
    public void updateUsersDay(Day day) {
        dayRepository.save(day);
    }

    @Override
    public void deleteUsersDay(Day day) {
        dayRepository.delete(day);
    }

    @Override
    public Optional<Day> getUsersDayByDate(LocalDate date, Long userID) {
        return dayRepository.getDayByDateAndUserId(date, userID);
    }

    @Override
    public boolean existsDayByUserIDAndDate(LocalDate date, Long userID) {
        return dayRepository.existsDayByDateAndUserId(date, userID);
    }
}
