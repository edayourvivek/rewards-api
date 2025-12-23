package com.rewards.rewardsapi.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class RewardServiceTest {

    private final RewardService service = new RewardService(null);

    @Test
    void calculatePoints_shouldFollowRewardRules() {

        assertEquals(0, service.calculatePoints(BigDecimal.valueOf(40)));
        assertEquals(0, service.calculatePoints(BigDecimal.valueOf(50)));

        assertEquals(20, service.calculatePoints(BigDecimal.valueOf(70)));
        assertEquals(50, service.calculatePoints(BigDecimal.valueOf(100)));

        assertEquals(90, service.calculatePoints(BigDecimal.valueOf(120)));
        assertEquals(250, service.calculatePoints(BigDecimal.valueOf(200)));
    }
}
