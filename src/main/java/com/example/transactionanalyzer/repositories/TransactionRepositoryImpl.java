package com.example.transactionanalyzer.repositories;

import com.example.transactionanalyzer.model.Transaction;

import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionRepositoryImpl(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public TransactionFilterQuery filterQuery() {
        return new TransactionFilterQuery(transactions);
    }
}
