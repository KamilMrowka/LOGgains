package com.log.gains.period.week;

import com.log.gains.date.DateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/week")
public class WeekController {

    private final WeekService weekService;
    private final DateService dateService;

    public WeekController(WeekService weekService, DateService dateService) {
        this.weekService = weekService;
        this.dateService = dateService;
    }

    @GetMapping
    public Object getWeekData (@RequestParam("date") String date) {
        return weekService.getUsersWeekMedianWeight(dateService.parseDate(date));
    }

}
