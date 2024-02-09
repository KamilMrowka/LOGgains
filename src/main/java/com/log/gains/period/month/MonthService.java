package com.log.gains.period.month;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MonthService {

    private final MonthDao monthDao;

    public MonthService(MonthDao monthDao) {
        this.monthDao = monthDao;
    }



    private Long createMonth (LocalDate includedDay) {
        LocalDate firstDay = includedDay.withDayOfMonth(1);
        int lengthOfMonth = firstDay.lengthOfMonth();
        LocalDate lastDay = firstDay.plusDays(lengthOfMonth - 1);
        Month month = new Month(firstDay, lastDay);
        monthDao.saveMonth(month);
        return month.getId();
    }

    public Long getCorrespondingMonthId (LocalDate date) {
        LocalDate firstDay = date.withDayOfMonth(1);
        Long correspondingMonthId;
        Optional<Month> savedMonth = monthDao.getMonthByFirstDay(firstDay);
        if (savedMonth.isEmpty()) {
            correspondingMonthId = createMonth(firstDay);
        } else {
            correspondingMonthId = savedMonth.get().getId();
        }
        return correspondingMonthId;
    }

}
