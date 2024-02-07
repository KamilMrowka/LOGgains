package com.log.gains.day;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/day")
@RestController
public class DayController {
    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @PostMapping
    public void createNewDay (@RequestBody RequestNewDayDTO dayDTO) {
        dayService.createDay(dayDTO);
    }

    @DeleteMapping
    public void deleteExistingDay (@RequestBody String date) {
        dayService.deleteExistingDay(date);
    }

    @GetMapping("/all")
    public List<Day> getAllDays () {
        return dayService.getUsersDays();
    }

    @PutMapping
    public void updateDay (@RequestBody RequestedDayUpdateDTO updateDTO) {
        dayService.updateUsersDay(updateDTO);
    }
}
