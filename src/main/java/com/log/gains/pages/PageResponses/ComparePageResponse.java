package com.log.gains.pages.PageResponses;

import com.log.gains.graph.GraphData;
import com.log.gains.period.Analysis;
import lombok.Data;

import java.util.List;

@Data
public class ComparePageResponse {
    private List<GraphData> weekOne;
    private List<GraphData> weekTwo;
    private List<GraphData> monthOne;
    private List<GraphData> monthTwo;
    private Analysis weekOneAnalysis;
    private Analysis weekTwoAnalysis;
    private Analysis monthOneAnalysis;
    private Analysis monthTwoAnalysis;
}