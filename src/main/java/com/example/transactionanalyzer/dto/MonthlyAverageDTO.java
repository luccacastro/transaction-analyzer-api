package com.example.transactionanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class MonthlyAverageDTO {
    private String category;
    private String year;
    private Map<String, Double> monthlyAverages;

    public MonthlyAverageDTO(String category, String year, Map<String, Double> monthlyAverages) {
        this.category = category;
        this.year = year;
        this.monthlyAverages = monthlyAverages;
    }

}
