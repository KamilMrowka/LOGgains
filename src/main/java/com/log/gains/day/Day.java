package com.log.gains.day;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
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
            nullable = false
    )
    private Long weekId;
    @Column (
            nullable = false
    )
    private Long monthId;

    private float weightMeasurement;

    private float caloriesConsumed;
    @Column(
            nullable = false
    )
    private LocalDate date;

    private String fDate;

    private String weekDay;


    public Day() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Double.compare(weightMeasurement, day.weightMeasurement) == 0 && Objects.equals(dayId, day.dayId) && Objects.equals(caloriesConsumed, day.caloriesConsumed) && Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayId, weightMeasurement, caloriesConsumed, date);
    }

    @Override
    public String toString() {
        return "Day{" +
                "dayId=" + dayId +
                ", weightMeasurement=" + weightMeasurement +
                ", caloriesConsumed=" + caloriesConsumed +
                ", dataDate=" + date +
                '}';
    }
}
