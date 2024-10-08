package com.log.gains.pages.PageResponses;

import com.log.gains.graph.GraphData;
import com.log.gains.period.Analysis;

import java.util.ArrayList;

public record CalendarPageResponse(ArrayList<ArrayList<GraphData>> weeks, Analysis monthAnalysis) {
}
