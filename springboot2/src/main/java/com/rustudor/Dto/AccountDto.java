package com.rustudor.Dto;

import com.rustudor.entity.CurrencyType;

import java.util.Date;

public class AccountDto {
    private int id;
    private Date dateOpened;
    private Double balance;
    private CurrencyType currency;

    public AccountDto() {
    }

    public AccountDto(int id, Date dateOpened, Double balance, CurrencyType currency) {
        this.id = id;
        this.dateOpened = dateOpened;
        this.balance = balance;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
}
