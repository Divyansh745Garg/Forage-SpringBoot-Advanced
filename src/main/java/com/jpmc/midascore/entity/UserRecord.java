package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class UserRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private float balance;

    // 1. The "No-Args" constructor (Required by JPA)
    public UserRecord() {
    }

    // 2. The "Full" constructor (Required by the Test Populator)
    public UserRecord(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    // Getters and Setters
    public long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getBalance() { return balance; }
    public void setBalance(float balance) { this.balance = balance; }
}