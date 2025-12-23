package com.rewards.rewardsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyReward {

    private int year;
    private String month;
    private BigDecimal totalAmount;
    private int rewardPoints;
}
