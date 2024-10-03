package com.log.gains.pages.PageResponses;
import java.util.ArrayList;
import com.log.gains.day.Day;
import com.log.gains.graph.GraphData;
import com.log.gains.period.Analysis;
import com.log.gains.period.week.Week;

public record HomePageResponse(
        Analysis weekAnalysis,
        ArrayList<GraphData> graphData
) {}
