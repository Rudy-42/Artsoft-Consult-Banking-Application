package com.rustudor.entity;

import javax.persistence.*;
import java.sql.Date;



@Entity
@Table
public class Exchange {
    @Id
    @Column(nullable = false,updatable = false)
    private Date day;
    private double usd;
    private double eur;
    private double usdBuy;
    private double usdSell;
    private double eurBuy;
    private double eurSell;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getEur() {
        return eur;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }

    public double getUsdBuy() {
        return usdBuy;
    }

    public void setUsdBuy(double usdBuy) {
        this.usdBuy = usdBuy;
    }

    public double getUsdSell() {
        return usdSell;
    }

    public void setUsdSell(double usdSell) {
        this.usdSell = usdSell;
    }

    public double getEurBuy() {
        return eurBuy;
    }

    public void setEurBuy(double eurBuy) {
        this.eurBuy = eurBuy;
    }

    public double getEurSell() {
        return eurSell;
    }

    public void setEurSell(double eurSell) {
        this.eurSell = eurSell;
    }

}
