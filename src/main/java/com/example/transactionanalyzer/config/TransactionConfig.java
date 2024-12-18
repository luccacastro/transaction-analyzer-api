package com.example.transactionanalyzer.config;

import com.example.transactionanalyzer.model.Transaction;

import com.example.transactionanalyzer.repositories.TransactionRepository;
import com.example.transactionanalyzer.repositories.TransactionRepositoryImpl;
import com.example.transactionanalyzer.services.TransactionService;
import com.example.transactionanalyzer.utils.JsonLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TransactionConfig {

    @Value("${transactions.json.path}")
    private String transactionsJsonPath;

    @Bean
    public List<Transaction> transactions() {
        return JsonLoader.loadTransactionsFromJson(transactionsJsonPath);
    }

    @Bean
    public TransactionRepository transactionRepository(List<Transaction> transactions) {
        return new TransactionRepositoryImpl(transactions);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        return new TransactionService(transactionRepository);
    }
}
