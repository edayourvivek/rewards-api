package com.rewards.rewardsapi.controller;

import com.rewards.rewardsapi.model.RewardSummary;
import com.rewards.rewardsapi.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping
    public List<RewardSummary> getRewards() {
        log.info("GET /api/rewards called");
        return rewardService.calculateRewards();
    }
}
