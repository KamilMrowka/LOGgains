package com.log.gains.period.month;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MonthService {

    private final MonthDao monthDao;

    public MonthService(MonthDao monthDao) {
        this.monthDao = monthDao;
    }

    public Long createCurrentMonth() {
        return createMonth(LocalDate.now());
    }

    public Long createMonth (LocalDate includedDay) {
        LocalDate firstDay = includedDay.withDayOfMonth(1);
        int lengthOfMonth = firstDay.lengthOfMonth();
        LocalDate lastDay = firstDay.plusDays(lengthOfMonth - 1);
        Month month = new Month(firstDay, lastDay);
        monthDao.saveMonth(month);
        return month.getId();
    }
}
