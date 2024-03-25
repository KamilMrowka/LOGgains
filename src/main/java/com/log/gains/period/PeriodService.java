package com.log.gains.period;

import com.log.gains.day.Day;
import com.log.gains.period.month.MonthService;
import com.log.gains.period.week.WeekService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeriodService {

    public double getMedianWeight (List<Day> days) {
        if (days.isEmpty()) {
            return 0;
        }
        List<Double> measurements = new ArrayList<>();
        double median;
        for (Day day: days) {
            measurements.add(day.getWeightMeasurement());
        }
        Collections.sort(measurements);
        int measurementsAmount = measurements.size();
        if (measurementsAmount % 2 == 0) {
            median = (measurements.get(measurementsAmount / 2 - 1) + measurements.get(measurementsAmount / 2)) / 2;
        } else {
            median = measurements.get(measurementsAmount / 2);
        }

        return median;
    }

    public double getCalculatedWeightChange (double medianBefore, double medianAfter) {
        return medianAfter - medianBefore;
    }

//    public Long getCorrespondingWeekId (LocalDate date) {
//        return weekService.getCorrespondingWeekId(date);
//    }
//
//    public Long getCorrespondingMonthId (LocalDate date) {
//        return monthService.getCorrespondingMonthId(date);
//    }
}
