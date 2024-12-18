package com.example.transactionanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MinAmountDTO {
    private String category;
    private double minAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public MinAmountDTO(String category, double minAmount, LocalDate startDate, LocalDate endDate) {
        this.category = category;
        this.minAmount = minAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
