package com.example.transactionanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryTotalDTO {
    private String category;
    private double totalAmount;

    public CategoryTotalDTO(String category, double totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

}
