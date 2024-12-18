package com.example.transactionanalyzer;
import com.example.transactionanalyzer.services.TransactionService;
import com.example.transactionanalyzer.utils.JsonLoader;

import com.example.transactionanalyzer.model.Transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TransactionAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionAnalyzerApplication.class, args);
    }

}
