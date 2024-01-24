package com.log.gains.period.day;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;
    private double weightMeasurement;
    private double caloriesConsumed;
    private LocalDate dataDate;

    public Day(Long dayId, double weightMeasurement, double caloriesConsumed) {
        this.dayId = dayId;
        this.weightMeasurement = weightMeasurement;
        this.caloriesConsumed = caloriesConsumed;
        this.dataDate = LocalDate.now();
    }

    public Day(double weightMeasurement, double caloriesConsumed, LocalDate dataDate) {
        this.weightMeasurement = weightMeasurement;
        this.caloriesConsumed = caloriesConsumed;
        this.dataDate = dataDate;
    }

    public Day(double weightMeasurement, double caloriesConsumed) {
        this.weightMeasurement = weightMeasurement;
        this.caloriesConsumed = caloriesConsumed;
        this.dataDate = LocalDate.now();
    }

    public Day() {
    }

    public Long getDayId() {
        return dayId;
    }

    public double getWeightMeasurement() {
        return weightMeasurement;
    }

    public void setWeightMeasurement(double weightMeasurement) {
        this.weightMeasurement = weightMeasurement;
    }

    public double getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(double caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Double.compare(weightMeasurement, day.weightMeasurement) == 0 && Objects.equals(dayId, day.dayId) && Objects.equals(caloriesConsumed, day.caloriesConsumed) && Objects.equals(dataDate, day.dataDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayId, weightMeasurement, caloriesConsumed, dataDate);
    }

    @Override
    public String toString() {
        return "Day{" +
                "dayId=" + dayId +
                ", weightMeasurement=" + weightMeasurement +
                ", caloriesConsumed=" + caloriesConsumed +
                ", dataDate=" + dataDate +
                '}';
    }
}
