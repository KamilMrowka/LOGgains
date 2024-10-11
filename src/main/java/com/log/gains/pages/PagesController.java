package com.log.gains.pages;
import com.log.gains.pages.PageResponses.CalendarPageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.log.gains.pages.PageResponses.ComparePageResponse;
import com.log.gains.pages.PageResponses.HomePageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pages")
@RequiredArgsConstructor
public class PagesController {
    
    private final PagesService pagesService;

    @GetMapping("/homePage")
    public HomePageResponse homePageResponse () {
       return pagesService.construcHomePageResponse(); 
    }

    @GetMapping("/calendarPage")
    public CalendarPageResponse calendarPageResponse(@RequestParam("date") String date) {
        return pagesService.constructCalendarPageResponse(date);
    }

    @GetMapping("/calendarPage/test")
    public CalendarPageResponse calendarPageTestResponse(@RequestParam("date") String date) {
        return pagesService.constructCalendarPageResponse(date);
    }
}
