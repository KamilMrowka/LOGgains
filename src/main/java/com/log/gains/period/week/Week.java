package com.log.gains.period.week;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private LocalDate lastDate;
}
