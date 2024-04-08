package com.log.gains.date;

import com.log.gains.exception.UnacceptedDateFormatException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateService {
    public LocalDate parseDate (String date) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new UnacceptedDateFormatException("Incorrect date format, or no date provided. F: YYYY-MM-DD");
        }
        return parsedDate;
    }
}
