package com.example.transactionanalyzer.model;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
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
