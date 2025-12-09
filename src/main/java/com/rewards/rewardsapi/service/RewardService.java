package com.rewards.rewardsapi.service;

import com.rewards.rewardsapi.model.RewardSummary;
import com.rewards.rewardsapi.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class RewardService {

    public int calculatePoints(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }

        if (amount > 100) {
            return (int) ((amount - 100) * 2 + 50); // 2 points over 100 + 1 point for 50–100
        } else if (amount > 50) {
            return (int) (amount - 50); // 1 point over 50
        }
        return 0;
    }

    public List<RewardSummary> calculateRewards(List<Transaction> transactions) {

        // customer -> (monthName -> points)
        Map<String, Map<String, Integer>> customerMonthly = new HashMap<>();

        for (Transaction tx : transactions) {
            int points = calculatePoints(tx.getAmount());
            if (points == 0) continue;

            String customerId = tx.getCustomerId();
            Month month = tx.getDate().getMonth();
            String monthKey = month.toString();

            Map<String, Integer> monthlyMap =
                    customerMonthly.computeIfAbsent(customerId, k -> new HashMap<>());

            monthlyMap.merge(monthKey, points, Integer::sum);
        }

        List<RewardSummary> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> entry : customerMonthly.entrySet()) {
            String customerId = entry.getKey();
            Map<String, Integer> monthlyPoints = entry.getValue();

            int total = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

            RewardSummary summary = new RewardSummary();
            summary.setCustomerId(customerId);
            summary.setMonthlyPoints(monthlyPoints);
            summary.setTotalPoints(total);

            result.add(summary);
        }

        return result;
    }
}
