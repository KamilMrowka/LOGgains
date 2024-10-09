package com.log.gains.pages;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Objects;

import com.log.gains.graph.GraphData;
import com.log.gains.graph.GraphDataService;
import com.log.gains.pages.PageResponses.CalendarPageResponse;
import com.log.gains.period.Analysis;
import org.springframework.stereotype.Service;

import com.log.gains.date.DateService;
import com.log.gains.day.Day;
import com.log.gains.day.DayService;
import com.log.gains.pages.PageResponses.HomePageResponse;
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
        ArrayList<String> weekDays = weekService.getWeekAsFormattedDays(week.getFirstDay());

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

    public CalendarPageResponse constructCalendarPageResponse(String month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        LocalDate localDate;
        ArrayList<ArrayList<GraphData>> weeks = new ArrayList<>();
        try {
            YearMonth yearMonth = YearMonth.parse(month, formatter);
            localDate = yearMonth.atDay(1);
        } catch (Exception e) {
           throw new RuntimeException("Couldn't parse the given month, correct format: mm-yyyy, date received " + month);
        }

        Long monthId = monthService.getCorrespondingMonthId(localDate);
        ArrayList<Day> dayList = monthService.findUsersDaysByMonthId(monthId);
        ArrayList<GraphData> week0;
        ArrayList<GraphData> week1;
        ArrayList<GraphData> week2;
        ArrayList<GraphData> week3;
        ArrayList<GraphData> week4;
        ArrayList<GraphData> week5;
        ArrayList<GraphData> week6;
        ArrayList<String> weekDays;
        int daysFromMonthBefore;

        Analysis monthAnalysis = new Analysis();
        monthAnalysis.setHighestWeight(periodService.getHighestWeight(dayList));
        monthAnalysis.setLowestWeight(periodService.getLowestWeight(dayList));
        monthAnalysis.setMedianWeight(periodService.getMedianWeight(dayList));
        monthAnalysis.setAverageCalories(periodService.getAverageCalories(dayList));


        // Initialize week0
        if (Objects.equals(localDate.getDayOfWeek().getValue(), 1)) {
            weekDays = weekService.getWeekAsFormattedDays(localDate);
            week0 = graphDataService.constructGraphData(weekDays, dayList);

            weeks.add(week0);
        } else {
            // Check how many days have to be in the week 0 from the month before
            int monthStartsAtDayOfWeek = localDate.getDayOfWeek().getValue();
            daysFromMonthBefore = monthStartsAtDayOfWeek - 1;


            weekDays = weekService.getWeekAsFormattedDays(localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));

            week0 = graphDataService.constructGraphData(weekDays.subList(0, daysFromMonthBefore), dayList, true, -98);
            week0.addAll(graphDataService.constructGraphData(weekDays.subList(daysFromMonthBefore, weekDays.size()), dayList, true));
            weeks.add(week0);
        }

        // Week 1
        weekDays = weekService.getWeekAsFormattedDays(
                        localDate
                        .plusWeeks(1)
                        .with(TemporalAdjusters
                                .previousOrSame(DayOfWeek.MONDAY)
                        )
        );

        week1 = graphDataService.constructGraphData(weekDays, dayList, true);
        weeks.add(week1);

        // Week2
        weekDays = weekService.getWeekAsFormattedDays(
                localDate
                        .plusWeeks(2)
                        .with(TemporalAdjusters
                                .previousOrSame(DayOfWeek.MONDAY)
                        )
        );

        week2 = graphDataService.constructGraphData(weekDays, dayList, true);
        weeks.add(week2);

        // Week3

        weekDays = weekService.getWeekAsFormattedDays(
                localDate
                        .plusWeeks(3)
                        .with(
                                TemporalAdjusters
                                .previousOrSame(DayOfWeek.MONDAY)
                        )
        );
        week3 = graphDataService.constructGraphData(weekDays, dayList, true);
        weeks.add(week3);

        // Week 4 and 5
        // If week4 month ends before sunday then week5 is empty
        // if week4 ends at friday(example) then add 2 days from next month

        LocalDate week4Start = localDate.plusWeeks(4).with(
                TemporalAdjusters.previousOrSame((DayOfWeek.MONDAY))
        );

        // Have to check whether the monday is in the same month
        // If February had started at monday it could end on week3
        // Then weeks have a len of 4

        if (week4Start.getMonth().getValue() != localDate.getMonthValue()) {

            return new CalendarPageResponse(
                    weeks,
                    monthAnalysis
            );
        }

        int week4Len = 0;
        boolean hasMonthGot6Rows = true;
        LocalDate lastDayOfTheMonth = week4Start.with(TemporalAdjusters.lastDayOfMonth());

        // Calendar could have 6 rows if a month starts at sunday
        // or other day close to the end of a week
        // Then we're returning 6 weeks (Arrays)

        for (LocalDate day = week4Start;
            day.isBefore(lastDayOfTheMonth.plusDays(1)) &&
            day.isBefore(
                    week4Start.with(
                    TemporalAdjusters.
                            nextOrSame(DayOfWeek.SUNDAY))
                            .plusDays(1)
            );
            day = day.plusDays(1)
        ) {
            week4Len++;
            if (day.plusDays(1).equals(
                    day.with(TemporalAdjusters.firstDayOfNextMonth()))
            ) {
               hasMonthGot6Rows = false;
            }
        }

        if (week4Len == 7 && !hasMonthGot6Rows) {
            weekDays = weekService.getWeekAsFormattedDays(week4Start);
            week4 = graphDataService.constructGraphData(weekDays, dayList, true);
            weeks.add(week4);

            return new CalendarPageResponse(
                    weeks,
                    monthAnalysis
            );
        } else if (week4Len < 7) {
           weekDays = weekService.getWeekAsFormattedDays(week4Start);
           week4 = graphDataService.constructGraphData(weekDays.subList(0, week4Len), dayList, true);
           week4.addAll(graphDataService.constructGraphData(weekDays.subList(week4Len, weekDays.size()), dayList, true, -98));
           weeks.add(week4);

           return new CalendarPageResponse(
                   weeks,
                   monthAnalysis
           );
        } else {
            weekDays = weekService.getWeekAsFormattedDays(week4Start);
            week4 = graphDataService.constructGraphData(weekDays, dayList, true);
            weeks.add(week4);

            //week5
            weekDays = weekService.getWeekAsFormattedDays(week4Start.plusWeeks(1));
            int leftDaysInCurrentMonth = week4Start.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth() - week4Start.plusWeeks(1).getDayOfMonth()+1;

            week5 = graphDataService.constructGraphData(weekDays.subList(0, leftDaysInCurrentMonth), dayList, true);
            week5.addAll(graphDataService.constructGraphData(weekDays.subList(leftDaysInCurrentMonth, weekDays.size()), dayList, true, -98));
            weeks.add(week5);

            return new CalendarPageResponse(
                    weeks,
                    monthAnalysis
            );
        }
    }
}
