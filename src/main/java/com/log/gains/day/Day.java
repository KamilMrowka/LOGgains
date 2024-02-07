package com.log.gains.day;

import jakarta.persistence.*;
import org.hibernate.annotations.NotFound;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (
        name = "_day"
)
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;
    @Column (
            nullable = false
    )
    private Long userId;
    @Column (
            nullable = true
    )
    private double weightMeasurement;
    @Column (
            nullable = true
    )
    private double caloriesConsumed;
    @Column(
            nullable = false
    )
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

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
