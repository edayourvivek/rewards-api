package com.rewards.rewardsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSummary {
    private String customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;
}
