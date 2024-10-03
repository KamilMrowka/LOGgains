package com.log.gains.pages;

import java.time.LocalDate;
import java.util.ArrayList;

import com.log.gains.graph.GraphData;
import com.log.gains.graph.GraphDataService;
import com.log.gains.period.Analysis;
import org.springframework.stereotype.Service;

import com.log.gains.date.DateService;
import com.log.gains.day.Day;
import com.log.gains.day.DayService;
import com.log.gains.pages.PageResponses.ComparePageResponse;
import com.log.gains.pages.PageResponses.HomePageResponse;
import com.log.gains.period.PeriodComparison;
import com.log.gains.period.PeriodService;
import com.log.gains.period.month.MonthService;
import com.log.gains.period.week.Week;
import com.log.gains.period.week.WeekService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagesService {

    private final WeekService weekService;
    private final MonthService monthService;
    private final PeriodService periodService;
    private final DateService dateService;
    private final DayService dayService;
    private final GraphDataService graphDataService;
    

    public HomePageResponse construcHomePageResponse() {
        Long weekId = weekService.getCorrespondingWeekId(LocalDate.now());
        ArrayList<Day> dayList = weekService.findUsersDaysByWeekId(weekId);
        Week week = weekService.getWeek(LocalDate.now());
        ArrayList<String> weekDays = weekService.getWeekDays(week.getFirstDay());

        float medianWeight = periodService.getMedianWeight(dayList);
        float avgCalories = periodService.getAverageCalories(dayList);
        float lowestWeight = periodService.getLowestWeight(dayList);
        float highestWeight = periodService.getHighestWeight(dayList);
        ArrayList<GraphData> graphData = graphDataService.constructGraphData(weekDays, dayList);

        Analysis weekAnalysis = new Analysis();
        weekAnalysis.setAverageCalories(avgCalories);
        weekAnalysis.setMedianWeight(medianWeight);
        weekAnalysis.setLowestWeight(lowestWeight);
        weekAnalysis.setHighestWeight(highestWeight);

        return new HomePageResponse(
                weekAnalysis,
                graphData
        );
    }
}
