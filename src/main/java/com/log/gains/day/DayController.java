package com.log.gains.day;

import com.log.gains.date.DateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/day")
@RestController
public class DayController {
    private final DayService dayService;

    public DayController(DayService dayService, DateService dateService) {
        this.dayService = dayService;
    }

    @PostMapping
    public void createNewDay (@RequestBody RequestNewDayDTO dayDTO) {
        // Long userId = getAuthUser();
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
