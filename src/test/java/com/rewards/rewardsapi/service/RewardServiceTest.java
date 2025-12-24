package com.rewards.rewardsapi.service;

import com.rewards.rewardsapi.model.RewardSummary;
import com.rewards.rewardsapi.model.Transaction;
import com.rewards.rewardsapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService service;


    @Test
    void shouldCalculateRewardsForValidTransactions() {
        Transaction tx = new Transaction(
                1L,
                "C1",
                BigDecimal.valueOf(120),
                LocalDate.now()
        );

        when(transactionRepository.findByDateAfter(any()))
                .thenReturn(List.of(tx));

        List<RewardSummary> result = service.calculateRewards();

        assertEquals(1, result.size());
        assertEquals("C1", result.get(0).getCustomerId());
        assertEquals(90, result.get(0).getTotalPoints());
    }


    @Test
    void shouldGiveZeroPointsForAmountEqualTo50() {
        Transaction tx = new Transaction(
                1L,
                "C1",
                BigDecimal.valueOf(50),
                LocalDate.now()
        );

        when(transactionRepository.findByDateAfter(any()))
                .thenReturn(List.of(tx));

        List<RewardSummary> result = service.calculateRewards();

        assertEquals(0, result.get(0).getTotalPoints());
    }

    @Test
    void shouldGive50PointsForAmountEqualTo100() {
        Transaction tx = new Transaction(
                1L,
                "C1",
                BigDecimal.valueOf(100),
                LocalDate.now()
        );

        when(transactionRepository.findByDateAfter(any()))
                .thenReturn(List.of(tx));

        List<RewardSummary> result = service.calculateRewards();

        assertEquals(50, result.get(0).getTotalPoints());
    }

    @Test
    void shouldSkipTransactionWhenAmountIsNull() {
        Transaction tx = new Transaction(
                1L,
                "C1",
                null,
                LocalDate.now()
        );

        when(transactionRepository.findByDateAfter(any()))
                .thenReturn(List.of(tx));

        List<RewardSummary> result = service.calculateRewards();

        assertTrue(result.isEmpty());
    }


    @Test
    void shouldReturnEmptyListWhenNoTransactionsFound() {
        when(transactionRepository.findByDateAfter(any()))
                .thenReturn(Collections.emptyList());

        List<RewardSummary> result = service.calculateRewards();

        assertTrue(result.isEmpty());
    }


}
