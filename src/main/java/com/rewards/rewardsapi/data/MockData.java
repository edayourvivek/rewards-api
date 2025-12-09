package com.rewards.rewardsapi.data;

import com.rewards.rewardsapi.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MockData {

    public List<Transaction> getTransactions() {
        return List.of(

                new Transaction("C001", 120, LocalDate.of(2024, 1, 10)),
                new Transaction("C001", 75,  LocalDate.of(2024, 1, 20)),
                new Transaction("C001", 200, LocalDate.of(2024, 2, 15)),


                new Transaction("C002", 50,  LocalDate.of(2024, 1, 5)),
                new Transaction("C002", 130, LocalDate.of(2024, 2, 25)),
                new Transaction("C002", 90,  LocalDate.of(2024, 3, 5))

              //  new Transaction("BAD", -50, LocalDate.of(2024, 1, 10))
        );
    }
}
