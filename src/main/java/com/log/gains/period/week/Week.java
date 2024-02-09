package com.log.gains.period.week;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "_week"
)
public class Week {
    @Id
    @GeneratedValue (
        strategy = GenerationType.IDENTITY
    )
    private Long id;
    private LocalDate firstDay;
    private LocalDate lastDay;

    public Week(LocalDate firstDay, LocalDate lastDay) {
        this.firstDay = firstDay;
        this.lastDay = lastDay;
    }

    public Week() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(LocalDate firstDay) {
        this.firstDay = firstDay;
    }

    public LocalDate getLastDay() {
        return lastDay;
    }

    public void setLastDay(LocalDate lastDate) {
        this.lastDay = lastDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week week = (Week) o;
        return Objects.equals(id, week.id) && Objects.equals(firstDay, week.firstDay) && Objects.equals(lastDay, week.lastDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstDay, lastDay);
    }

    @Override
    public String toString() {
        return "Week{" +
                "id=" + id +
                ", firstDay=" + firstDay +
                ", lastDate=" + lastDay +
                '}';
    }
}
