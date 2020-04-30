package com.rustudor.Dto;

import com.rustudor.entity.Account;

import java.sql.Timestamp;

public class TransferDto {

    private Timestamp time;
    private String type;
    private double amount;
    private int fromId;
    private int toId;

    public TransferDto() {
    }

    public TransferDto(Timestamp time, String type, double amount, int fromId, int toId) {
        this.time = time;
        this.type = type;
        this.amount = amount;
        this.fromId = fromId;
        this.toId = toId;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }
}
