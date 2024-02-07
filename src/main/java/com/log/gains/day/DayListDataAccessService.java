//package com.log.gains.day;
//
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Repository("list")
//public class DayListDataAccessService implements DayDAO {
//    private List<Day> days = new ArrayList<>();
//
//
//
//    @Override
//    public void addDay(Day day) {
//        days.add(day);
//    }
//
//    @Override
//    public List<Day> getAllDays() {
//        return days;
//    }
//
//    @Override
//    public void updateDay(Day day) {
//
//    }
//
//    @Override
//    public Optional<Day> getDayById(Long id) {
//        return days.stream().filter(d -> d.getDayId().equals(id)).findFirst();
//    }
//
//    @Override
//    public void deleteDay(Long id) {
//
//    }
//
//    @Override
//    public boolean existsDayByDate(LocalDate date) {
//        return false;
//    }
//
//    @Override
//    public Optional<Day> getDayByDate(LocalDate date) {
//        return Optional.empty();
//    }
//}
