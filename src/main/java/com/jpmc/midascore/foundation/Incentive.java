package com.jpmc.midascore.foundation;

public class Incentive {
    private float amount;

    // Default constructor is needed for JSON deserialization
    public Incentive() {}

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}