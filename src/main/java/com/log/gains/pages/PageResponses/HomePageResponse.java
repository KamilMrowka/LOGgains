package com.log.gains.pages.PageResponses;
import java.util.ArrayList;
import com.log.gains.day.Day;
import com.log.gains.period.week.Week;

public record HomePageResponse(ArrayList<Day> weekList, Double medianWeight, Double averageCalories, Week week) {
    
}
