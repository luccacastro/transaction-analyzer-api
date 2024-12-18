package com.example.transactionanalyzer.services;

import com.example.transactionanalyzer.dto.*;
import com.example.transactionanalyzer.exceptions.CategoryNotFoundException;
import com.example.transactionanalyzer.exceptions.UnableToCalculateException;
import com.example.transactionanalyzer.model.Transaction;
import com.example.transactionanalyzer.repositories.TransactionFilterQuery;
import com.example.transactionanalyzer.repositories.TransactionRepository;
import com.example.transactionanalyzer.utils.ErrorMessages;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public CategoryTransactionsDTO getTransactionsByCategory(String category) {
        List<Transaction> transactions = transactionRepository.filterQuery()
                .byCategory(category)
                .sortByLatest()
                .getTransactions();

        if (transactions.isEmpty()) {
            throw new CategoryNotFoundException(formatError(ErrorMessages.CATEGORY_NOT_FOUND, category));
        }
        return new CategoryTransactionsDTO(category, transactions);
    }

    public CategoryTotalDTO getTotalOutgoingForCategory(String category, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = fetchTransactionsForCalculation(category, startDate, endDate, ErrorMessages.UNABLE_TO_CALCULATE_TOTAL);
        return new CategoryTotalDTO(category, calculateTotalAmount(transactions));
    }

    public MaxAmountDTO getMaxAmountForCategory(String category, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = fetchTransactionsForCalculation(category, startDate, endDate, ErrorMessages.UNABLE_TO_CALCULATE_MAX);
        return new MaxAmountDTO(category, calculateMaxAmount(transactions), adjustDate(startDate), adjustDate(endDate));
    }

    public MinAmountDTO getMinAmountForCategory(String category, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = fetchTransactionsForCalculation(category, startDate, endDate, ErrorMessages.UNABLE_TO_CALCULATE_MIN);
        return new MinAmountDTO(category, calculateMinAmount(transactions), adjustDate(startDate), adjustDate(endDate));
    }

    public MonthlyAverageDTO getMonthlyAverageSpendForCategory(String category, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = fetchTransactionsForCalculation(category, startDate, endDate, ErrorMessages.UNABLE_TO_CALCULATE_AVERAGE);
        Map<String, Double> monthlyAverages = calculateMonthlyAverages(transactions);
        return new MonthlyAverageDTO(category, String.valueOf(startDate.getYear()), monthlyAverages);
    }

    private List<Transaction> fetchTransactionsForCalculation(String category, LocalDate startDate, LocalDate endDate, String errorMessage) {
        TransactionFilterQuery query = transactionRepository.filterQuery()
                .byCategory(category)
                .byDateRange(startDate, endDate);

        if (query.getTransactions().isEmpty()) {
            throw new UnableToCalculateException(formatError(errorMessage, category));
        }
        return query.getTransactions();
    }

    private double calculateTotalAmount(List<Transaction> transactions) {
        double sum = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        return roundToTwoDecimalPlaces(sum);
    }

    private double calculateMaxAmount(List<Transaction> transactions) {
        double max = transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
        return roundToTwoDecimalPlaces(max);
    }

    private double calculateMinAmount(List<Transaction> transactions) {
        double min = transactions.stream().mapToDouble(Transaction::getAmount).min().orElse(0.0);
        return roundToTwoDecimalPlaces(min);
    }

    private Map<String, Double> calculateMonthlyAverages(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        this::groupByYearMonth,
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(Transaction::getAmount),
                                this::roundToTwoDecimalPlaces)
                ));
    }

    private LocalDate adjustDate(LocalDate date) {
        return (date == null || date.equals(LocalDate.MIN)) ? null : date;
    }

    private String groupByYearMonth(Transaction transaction) {
        return YearMonth.from(transaction.getDate()).toString();
    }

    private double roundToTwoDecimalPlaces(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String formatError(String message, String category) {
        return String.format(message, category);
    }
}
