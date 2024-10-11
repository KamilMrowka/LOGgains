package com.log.gains.period;

import com.log.gains.day.Day;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeriodService {

    public float getMedianWeight (List<Day> days) {
        if (days.isEmpty()) {
            return 0;
        }
        List<Float> measurements = new ArrayList<>();
        float median;
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

        median = Math.round(median * 100.0f) / 100.0f;


        return median;
    }

    public float getAverageCalories (List<Day> daysList) {
        int numberOfDays = daysList.size();
        
        float sumOfCalories = 0;
        float avgCalories = 0;
        for (Day day : daysList) {
            if (day.getCaloriesConsumed() == 0) {
                numberOfDays--;
                continue;
            }
            sumOfCalories += day.getCaloriesConsumed();
        }
        if (numberOfDays == 0 | sumOfCalories == 0) {
            return 0;
        }
        avgCalories = sumOfCalories / numberOfDays;
        return avgCalories;
    }

    public PeriodComparison buildComparison(ArrayList<Day> p1, ArrayList<Day> p2) {
        
        float calculatedWeightChange = getMedianWeight(p1) - getMedianWeight(p2);
        float averageCaloriesChange = getAverageCalories(p1) - getAverageCalories(p2);

        return new PeriodComparison(calculatedWeightChange, averageCaloriesChange); 
    }

    public float getHighestWeight(ArrayList<Day> days) {
        float highestWeight = -1;

        for (Day day : days) {
            if (day.getWeightMeasurement() > highestWeight) {
                highestWeight = day.getWeightMeasurement();
            }
        }

        return highestWeight != -1 ? highestWeight : 0;
    }

    public float getLowestWeight(ArrayList<Day> days) {
        float lowestWeight = 501;

        for (Day day : days) {
            if (day.getWeightMeasurement() < lowestWeight) {
                lowestWeight = day.getWeightMeasurement();
            }
        }

        return lowestWeight != 501 ? lowestWeight : 0;
    }
}
