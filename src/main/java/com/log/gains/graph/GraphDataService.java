package com.log.gains.graph;

import com.log.gains.day.Day;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class GraphDataService {

    //TODO GraphData stores day Id (useful with put request when updating the day's values)
    public ArrayList<GraphData> constructGraphData(List<String> fDates, List<Day> days, int customCalories) {
        ArrayList<GraphData> graphDataArray = new ArrayList<>();
        for (int i = 0, j = 0; i < fDates.size(); i ++) {
            GraphData gData = new GraphData();
            gData.setDate(fDates.get(i));
            if (!days.isEmpty() && days.get(j).getFDate().equals(gData.getDate())) {
                gData.setCalories(days.get(j).getCaloriesConsumed());
                gData.setWeight(days.get(j).getWeightMeasurement());
                if (j < days.size() -1) j++;
            }
            if (gData.getCalories() != customCalories && gData.getCalories() == 0 && gData.getWeight() == 0) {
                gData.setCalories(customCalories);
            }
            graphDataArray.add(gData);
        }
        return graphDataArray;
    }

    public ArrayList<GraphData> constructGraphData(List<String> fDates, ArrayList<Day> days, Boolean cutDays, int customKcal) {

        if (!cutDays) {
           return constructGraphData(fDates, days, customKcal);
        }
        int startDaysAtIndx = -1;
        int firstDay = Integer.parseInt(fDates.get(0).split(Pattern.quote("."))[0]);

        for (int i=0; i < days.size(); i++) {
            if (days.get(i).getDate().getDayOfMonth() >= firstDay) {
                startDaysAtIndx = i;
                break;
            }
        }

        if (startDaysAtIndx == -1) {
            return constructGraphData(fDates, days.subList(0,0), customKcal);
        }

        List<Day> subListDays = (days.subList(startDaysAtIndx, days.size()));
        return constructGraphData(fDates, subListDays, customKcal);

    }

    public ArrayList<GraphData> constructGraphData(List<String> fDates, ArrayList<Day> days, Boolean cutDays) {
        return constructGraphData(fDates, days, cutDays, 0);
    }

    public ArrayList<GraphData> constructGraphData(List<String> fDates, List<Day> days) {
        return constructGraphData(fDates, days, 0);
    }

}
