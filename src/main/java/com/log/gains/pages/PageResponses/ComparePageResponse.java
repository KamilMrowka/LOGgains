package com.log.gains.pages.PageResponses;

import java.util.ArrayList;

import com.log.gains.day.Day;

public record ComparePageResponse(

   ArrayList<Day> WeekOne,

//      array of { date { dd.mm, weekday }, kg, kcal } ready for graph render

   ArrayList<Day> weekTwo,

//      if same as week one, empty

   Object weekComparison,

//      lost/gained ... kg
//      around ... % gain/loss per week 
//      average kcal more/less by...
//      average daily deficit/surplus throughout between these periods was ... kg 

   ArrayList<Day> monthOne,
   ArrayList<Day> monthTwo, 
   Object monthComparison

//      IF ALL ARE EMPTY THROW ERROR
//      IF SAME MONTH SHOW NO MONTH COMPARISON DATA
//      IF SAME WEEK SHOW NO COMPARISON DATA, JUST GRAPHS
) {
    
}
