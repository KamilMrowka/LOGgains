package com.log.gains.period.week;

import com.log.gains.day.Day;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/week")
@RequiredArgsConstructor
public class WeekController {

    private WeekService weekService;

    // TODO
    public List<Day> getWeek (LocalDate date) {
        return new ArrayList<Day>();
    }
}
