package com.rewards.rewardsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;
}
