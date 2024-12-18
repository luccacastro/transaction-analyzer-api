package com.example.transactionanalyzer.repositories;

import com.example.transactionanalyzer.model.Transaction;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionFilterQuery {

    private List<Transaction> transactions;

    public TransactionFilterQuery(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public TransactionFilterQuery byCategory(String category) {
        if (category != null) {
            transactions.removeIf(t -> !t.getCategory().equalsIgnoreCase(category));
        }
        return this;
    }

    public TransactionFilterQuery byDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null || endDate != null) {
            transactions.removeIf(t -> (startDate != null && t.getDate().isBefore(startDate)) ||
                    (endDate != null && t.getDate().isAfter(endDate)));
        }
        return this;
    }

    public TransactionFilterQuery byProduct(String product) {
        if (product != null) {
            transactions.removeIf(t -> !t.getProduct().equalsIgnoreCase(product));
        }
        return this;
    }

    public TransactionFilterQuery byVendor(String vendor) {
        if (vendor != null) {
            transactions.removeIf(t -> !t.getVendor().equalsIgnoreCase(vendor));
        }
        return this;
    }

    public TransactionFilterQuery sortByLatest() {
        transactions.sort(Comparator.comparing(Transaction::getDate).reversed()); // In-place sort
        return this;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
