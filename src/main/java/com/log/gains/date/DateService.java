package com.log.gains.date;

import com.log.gains.exception.UnacceptedDateFormatException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {
    public LocalDate parseDate (String date) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new UnacceptedDateFormatException("Incorrect date format. F: YYYY-MM-DD");
        }
        return parsedDate;
    }

    public String formatDate (LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        // int year = date.getYear();

        return (day < 10 ? ("0" + day) : day) + "." + (month < 10 ? ("0" + month) : month);
    }


    public LocalDate parseDate (String date, boolean returnsNull, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
