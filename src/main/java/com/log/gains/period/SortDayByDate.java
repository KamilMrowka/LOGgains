package com.log.gains.period;

import java.util.Comparator;

import com.log.gains.day.Day;

public class SortDayByDate implements Comparator<Day>{
   @Override
   public int compare(Day o1, Day o2) {
       return o1.getDate().compareTo(o2.getDate());
   } 
}
