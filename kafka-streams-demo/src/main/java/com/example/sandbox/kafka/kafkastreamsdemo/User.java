package com.example.sandbox.kafka.kafkastreamsdemo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private String username;
    private BigDecimal balance;
}
