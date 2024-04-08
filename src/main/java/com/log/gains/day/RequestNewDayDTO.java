package com.log.gains.day;

import java.time.LocalDate;

public record RequestNewDayDTO(Double caloriesConsumed, Double weightMeasurement, String date) {
}
