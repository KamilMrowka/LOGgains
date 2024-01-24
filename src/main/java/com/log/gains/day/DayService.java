package com.log.gains.period.day;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayService {
    private final DayDAO dayDAO;

    public DayService(DayDAO dayDAO) {
        this.dayDAO = dayDAO;
    }

    public void createDay (RequestNewDayDTO dayDTO) {
        Day day = new Day();

        if (dayDTO.weightMeasurement() != null) {
            day.setWeightMeasurement(dayDTO.weightMeasurement());
        }
        if (dayDTO.caloriesConsumed() != null) {
            day.setCaloriesConsumed(dayDTO.caloriesConsumed());
        }
        dayDAO.addDay(day);
    }

    public Day getDayById (Long id) {
        return dayDAO.getDayById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Incorrect ID of: [%s]"
                                .formatted(id)));
    }

    public List<Day> getAllDays () {
        return dayDAO.getAllDays();
    }
}
