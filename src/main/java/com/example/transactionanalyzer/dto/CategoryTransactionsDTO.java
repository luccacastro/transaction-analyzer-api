package com.example.transactionanalyzer.dto;

import com.example.transactionanalyzer.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryTransactionsDTO {
    private String category;
    private List<Transaction> transactions;

    public CategoryTransactionsDTO(String category, List<Transaction> transactions) {
        this.category = category;
        this.transactions = transactions;
    }

}
