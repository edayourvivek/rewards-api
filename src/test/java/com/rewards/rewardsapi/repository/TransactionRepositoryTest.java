package com.rewards.rewardsapi.repository;

import com.rewards.rewardsapi.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void findByDateAfter_shouldReturnOnlyRecentTransactions() {

        LocalDate cutoffDate = LocalDate.now().minusMonths(3);

        List<Transaction> transactions =
                transactionRepository.findByDateAfter(cutoffDate);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        transactions.forEach(tx ->
                assertTrue(tx.getDate().isAfter(cutoffDate))
        );
    }
}
