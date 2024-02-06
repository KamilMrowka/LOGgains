package com.log.gains.period.month;

import jakarta.persistence.*;
import java.time.LocalDate;

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
}

