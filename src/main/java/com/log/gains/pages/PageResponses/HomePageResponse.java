package com.log.gains.pages.PageResponses;
import java.util.ArrayList;
import com.log.gains.day.Day;
import com.log.gains.period.week.Week;

public record HomePageResponse(
        ArrayList<Day> dayList,
        float medianWeight,
        float averageCalories,
        Week week,
        ArrayList<String> weekDays,
        Day today,
        float lowestWeight,
        float highestWeight
) {}
