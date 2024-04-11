package com.log.gains.pages;

import java.time.LocalDate;
import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.log.gains.day.Day;
import com.log.gains.pages.PageResponses.HomePageResponse;
import com.log.gains.period.PeriodService;
import com.log.gains.period.week.Week;
import com.log.gains.period.week.WeekService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pages")
@RequiredArgsConstructor
public class PagesController {
    private final WeekService weekService;
    private final PeriodService periodService;

    @GetMapping("/homePage")
    public HomePageResponse homePageResponse () {
        Long weekId = weekService.getCorrespondingWeekId(LocalDate.now());
        ArrayList<Day> weekList = weekService.findUsersDaysByWeekId(weekId);
        Week week = weekService.getWeek(LocalDate.now().toString());
        double medianWeight = periodService.getMedianWeight(weekList);
        double avgCalories = periodService.getAverageCalories(weekList);
        HomePageResponse homePageResponse = new HomePageResponse(weekList,medianWeight,avgCalories, week);
        return homePageResponse;
   } 
}
