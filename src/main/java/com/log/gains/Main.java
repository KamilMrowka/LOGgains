package com.log.gains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    @Bean
//    CommandLineRunner runner (DayDatabaseDataAccessService dayDatabaseDataAccessService) {
//        return args -> {
//            Day day1 = new Day(87.5, 2500, LocalDate.of(2024, 1, 22));
//            Day day2 = new Day(88.5,2750, LocalDate.of(2024, 1, 21));
//            dayDatabaseDataAccessService.addDay(day1);
//            dayDatabaseDataAccessService.addDay(day2);
//        };
//    }
}