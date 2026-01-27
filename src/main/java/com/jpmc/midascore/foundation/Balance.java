package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {
    private String name;
    private float amount;

    // 1. Default constructor (for JSON)
    public Balance() {
    }

    // 2. The Old Constructor (to fix HealthController)
    public Balance(float amount) {
        this.amount = amount;
    }

    // 3. The New Constructor (for your Task 5 BalanceController)
    public Balance(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    // ... Keep all your getters and setters here ...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Balance {name='" + name + "', amount=" + amount + "}";
    }
}