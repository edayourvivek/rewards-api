package com.rewards.rewardsapi.service;

import com.rewards.rewardsapi.model.MonthlyAccumulator;
import com.rewards.rewardsapi.model.MonthlyReward;
import com.rewards.rewardsapi.model.RewardSummary;
import com.rewards.rewardsapi.model.Transaction;
import com.rewards.rewardsapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardService {

    private final TransactionRepository transactionRepository;

    public int calculatePoints(BigDecimal amount) {

        if (amount == null) {
            throw new IllegalArgumentException("Transaction amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }

        if (amount.compareTo(BigDecimal.valueOf(100)) > 0) {
            return amount
                    .subtract(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(2))
                    .add(BigDecimal.valueOf(50))
                    .intValue();
        }

        if (amount.compareTo(BigDecimal.valueOf(50)) > 0) {
            return amount
                    .subtract(BigDecimal.valueOf(50))
                    .intValue();
        }

        return 0;
    }


    public List<RewardSummary> calculateRewards() {

        List<Transaction> transactions = fetchRecentTransactions();
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<YearMonth, MonthlyAccumulator>> data =
                accumulateRewards(transactions);

        log.info("Reward calculation completed for {} customers", data.size());

        return buildResponse(data);
    }


    private List<Transaction> fetchRecentTransactions() {
        LocalDate cutoffDate = LocalDate.now().minusMonths(3);
        log.info("Starting reward calculation. Cutoff date: {}", cutoffDate);

        List<Transaction> transactions =
                transactionRepository.findByDateAfter(cutoffDate);

        log.info("Fetched {} transactions from database", transactions.size());
        return transactions;
    }

    private Map<String, Map<YearMonth, MonthlyAccumulator>>
    accumulateRewards(List<Transaction> transactions) {

        Map<String, Map<YearMonth, MonthlyAccumulator>> data = new HashMap<>();

        for (Transaction tx : transactions) {

            if (tx.getDate() == null || tx.getAmount() == null || tx.getCustomerId() == null) {
                continue;
            }

            int points = calculatePoints(tx.getAmount());
            YearMonth ym = YearMonth.from(tx.getDate());

            data.computeIfAbsent(tx.getCustomerId(), k -> new HashMap<>())
                    .computeIfAbsent(ym, k -> new MonthlyAccumulator());

            MonthlyAccumulator acc = data.get(tx.getCustomerId()).get(ym);

            acc.points += points;
            acc.totalAmount = acc.totalAmount.add(tx.getAmount());
        }

        return data;
    }

    private List<RewardSummary> buildResponse(
            Map<String, Map<YearMonth, MonthlyAccumulator>> data) {

        List<RewardSummary> response = new ArrayList<>();

        for (var customerEntry : data.entrySet()) {

            List<MonthlyReward> monthly = new ArrayList<>();
            int totalPoints = 0;

            for (var monthEntry : customerEntry.getValue().entrySet()) {
                YearMonth ym = monthEntry.getKey();
                MonthlyAccumulator acc = monthEntry.getValue();

                monthly.add(new MonthlyReward(
                        ym.getYear(),
                        ym.getMonth().name(),
                        acc.totalAmount,
                        acc.points
                ));

                totalPoints += acc.points;
            }

            RewardSummary summary = new RewardSummary();
            summary.setCustomerId(customerEntry.getKey());
            summary.setMonthlyReward(monthly);
            summary.setTotalPoints(totalPoints);

            response.add(summary);
        }

        return response;
    }


}
