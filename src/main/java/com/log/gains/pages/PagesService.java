package com.log.gains.pages;

import java.time.LocalDate;
import java.util.ArrayList;

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
    

    public HomePageResponse construcHomePageResponse() {
        Long weekId = weekService.getCorrespondingWeekId(LocalDate.now());
        ArrayList<Day> dayList = weekService.findUsersDaysByWeekId(weekId);
        Week week = weekService.getWeek(LocalDate.now());
        ArrayList<String> weekDays = weekService.getWeekDays(week.getFirstDay());
        double medianWeight = periodService.getMedianWeight(dayList);
        double avgCalories = periodService.getAverageCalories(dayList);
        HomePageResponse homePageResponse = new HomePageResponse(dayList,medianWeight,avgCalories, week, weekDays);
        return homePageResponse;
    }

    public ComparePageResponse constructComparePageResponse(String date1, String date2) {

        boolean areDatesSameWeek = false;
        boolean areDatesSameMonth = false;
        LocalDate parsedDate1 = dateService.parseDate(date1);
        LocalDate parsedDate2 = dateService.parseDate(date2);
        Long weekId1;
        Long weekId2;
        Long monthId1;
        Long monthId2;
  
        if (parsedDate1.isAfter(parsedDate2)) {
            weekId1 = weekService.getCorrespondingWeekIdIfExists(parsedDate1);
            weekId2 = weekService.getCorrespondingWeekIdIfExists(parsedDate2);
            monthId1 = monthService.getCorrespondingMonthIdIfExists(parsedDate1);
            monthId2 = monthService.getCorrespondingMonthIdIfExists(parsedDate2);
        } else {
            weekId1 = weekService.getCorrespondingWeekIdIfExists(parsedDate2);
            weekId2 = weekService.getCorrespondingWeekIdIfExists(parsedDate1);
            monthId1 = monthService.getCorrespondingMonthIdIfExists(parsedDate2);
            monthId2 = monthService.getCorrespondingMonthIdIfExists(parsedDate1);
        }
        ArrayList<Day> weekOne;
        ArrayList<Day> weekTwo;
        ArrayList<Day> monthOne;
        ArrayList<Day> monthTwo;
        PeriodComparison weekComparison;
        PeriodComparison monthComparison;
        ComparePageResponse comparePageResponse;

        if (weekId1 == weekId2) {
            areDatesSameWeek = true;
            areDatesSameMonth = true;
        } else if (monthId1 == monthId2) {
            areDatesSameMonth = true;
        }

        weekOne = weekService.findUsersDaysByWeekId(weekId1);

        // same week & same month
        // not same week & same month
        // not same week & not same month

        if (areDatesSameWeek) {
            weekOne = weekService.findUsersDaysByWeekId(weekId1);
            weekTwo = null;
            monthOne = monthService.findUsersDaysByMonthId(monthId1);
            monthTwo = null;
            weekComparison = null;
            monthComparison = null;
            comparePageResponse = new ComparePageResponse(weekOne, weekTwo, weekComparison, monthOne, monthTwo, monthComparison);
            return comparePageResponse; 
        } else if (areDatesSameMonth) {
            weekOne = weekService.findUsersDaysByWeekId(weekId1);
            weekTwo = weekService.findUsersDaysByWeekId(weekId2);
            monthOne = monthService.findUsersDaysByMonthId(monthId1);
            monthTwo = null;
            weekComparison = periodService.builComparison(weekOne, weekTwo);
            monthComparison = null;
            comparePageResponse = new ComparePageResponse(weekOne, weekTwo, weekComparison, monthOne, monthTwo, monthComparison);
            return comparePageResponse;
        } else {
            
        }

        return null;
    }
}
