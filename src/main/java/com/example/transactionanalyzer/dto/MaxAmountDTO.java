package com.example.transactionanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MaxAmountDTO {
    private String category;
    private double maxAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public MaxAmountDTO(String category, double maxAmount, LocalDate startDate, LocalDate endDate) {
        this.category = category;
        this.maxAmount = maxAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
