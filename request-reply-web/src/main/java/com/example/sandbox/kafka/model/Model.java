package com.example.sandbox.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Model {
    private String firstParameter;
    private String secondParameter;
    private String acknowledgement;
}
