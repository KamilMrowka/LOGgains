package com.log.gains.period.month;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "_month"
)
public class Month {
    @Id
    @GeneratedValue (
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private LocalDate firstDay;
    private LocalDate lastDate;

    public Month(LocalDate firstDay, LocalDate lastDate) {
        this.firstDay = firstDay;
        this.lastDate = lastDate;
    }

    public Month() {
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

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Month month = (Month) o;
        return Objects.equals(id, month.id) && Objects.equals(firstDay, month.firstDay) && Objects.equals(lastDate, month.lastDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstDay, lastDate);
    }

    @Override
    public String toString() {
        return "Month{" +
                "id=" + id +
                ", firstDay=" + firstDay +
                ", lastDate=" + lastDate +
                '}';
    }
}

