package com.example.transactionanalyzer.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Comparable<Transaction> {
    private LocalDate date;
    private String vendor;
    private String product;
    private double amount;
    private String category;

    @Override
    public int compareTo(@NonNull Transaction o) {
        return this.date.compareTo(o.date);
    }
}
