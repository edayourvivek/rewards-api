package com.rewards.rewardsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSummary {
    private String customerId;
    private List<MonthlyReward> monthlyReward;
    private int totalPoints;
}
