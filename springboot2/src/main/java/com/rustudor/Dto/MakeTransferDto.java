package com.rustudor.Dto;

import com.rustudor.entity.Account;

import java.sql.Timestamp;

public class MakeTransferDto {

    private double amount;
    private int fromId;
    private int toId;


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
