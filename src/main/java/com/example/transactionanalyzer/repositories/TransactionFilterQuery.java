package com.example.transactionanalyzer.repositories;

import com.example.transactionanalyzer.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionFilterQuery {

    private final List<Transaction> originalTransactions;
    private final List<Transaction> filteredTransactions;

    public TransactionFilterQuery(List<Transaction> transactions) {
        this.originalTransactions = transactions;
        this.filteredTransactions = new ArrayList<Transaction>(transactions);
    }

    public TransactionFilterQuery byCategory(String category) {
        if (category != null) {
            filteredTransactions.removeIf(t -> !t.getCategory().equalsIgnoreCase(category));
        }
        return this;
    }

    public TransactionFilterQuery byDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null || endDate != null) {
            filteredTransactions.removeIf(t -> (startDate != null && t.getDate().isBefore(startDate)) ||
                    (endDate != null && t.getDate().isAfter(endDate)));
        }
        return this;
    }

    public TransactionFilterQuery byProduct(String product) {
        if (product != null) {
            filteredTransactions.removeIf(t -> !t.getProduct().equalsIgnoreCase(product));
        }
        return this;
    }

    public TransactionFilterQuery byVendor(String vendor) {
        if (vendor != null) {
            filteredTransactions.removeIf(t -> !t.getVendor().equalsIgnoreCase(vendor));
        }
        return this;
    }

    public TransactionFilterQuery sortByLatest() {
        filteredTransactions.sort(Comparator.comparing(Transaction::getDate).reversed());
        return this;
    }

    public List<Transaction> getTransactions() {
        return filteredTransactions;
    }
}
