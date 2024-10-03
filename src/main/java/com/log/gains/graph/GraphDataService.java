package com.log.gains.graph;

import com.log.gains.day.Day;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GraphDataService {

    public ArrayList<GraphData> constructGraphData(ArrayList<String> fDates, ArrayList<Day> days) {
        ArrayList<GraphData> graphDataArray = new ArrayList<>();
        for (int i = 0, j = 0; i < fDates.size() - 1; i ++) {
            GraphData gData = new GraphData();
            gData.setDate(fDates.get(i));
            if (!days.isEmpty() && days.get(j).getFDate().equals(gData.getDate())) {
                gData.setCalories(days.get(j).getCaloriesConsumed());
                gData.setWeight(days.get(j).getWeightMeasurement());
                if (j < days.size() -1) j++;
            }
            graphDataArray.add(gData);
        }
        return graphDataArray;
    }

}
