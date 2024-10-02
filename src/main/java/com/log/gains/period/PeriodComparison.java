package com.log.gains.period;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodComparison {
    private float calculatedWeightChange;
    private float averageCaloriesChange;

    public PeriodComparison (float calculatedWeightChange, float averageCaloriesChange) {
        this.averageCaloriesChange = averageCaloriesChange;
        this.calculatedWeightChange = calculatedWeightChange;
    }
}
