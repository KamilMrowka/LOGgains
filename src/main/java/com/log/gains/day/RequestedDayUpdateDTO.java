package com.log.gains.day;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public record RequestedDayUpdateDTO(String date, Double weightMeasurement, Double caloriesConsumed){}
