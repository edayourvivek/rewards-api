package com.rewards.rewardsapi.controller;

import com.rewards.rewardsapi.data.MockData;
import com.rewards.rewardsapi.model.RewardSummary;
import com.rewards.rewardsapi.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private  MockData mockData;

    @GetMapping
    public List<RewardSummary> getRewards() {
        return rewardService.calculateRewards(mockData.getTransactions());
    }
}
