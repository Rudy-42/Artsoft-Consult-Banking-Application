package com.rustudor.entity;



import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table
public class Account {
    @Id
    @GeneratedValue
    @Column(nullable = false,updatable = false)
    private int id;
    private Date dateOpened;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;
    @ManyToOne
    private User userFK;
    @OneToMany(mappedBy = "to")
    private Set<Transfer> sent = new HashSet<>();
    @OneToMany(mappedBy = "from")
    private Set<Transfer> received = new HashSet<>();


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserFK() {
        return userFK;
    }

    public void setUserFK(User userFK) {
        this.userFK = userFK;
    }

    public Set<Transfer> getSent() {
        return sent;
    }

    public void setSent(Set<Transfer> sent) {
        this.sent = sent;
    }

    public Set<Transfer> getReceived() {
        return received;
    }

    public void setReceived(Set<Transfer> received) {
        this.received = received;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return id == that.id &&
                Objects.equals(dateOpened, that.dateOpened) &&
                Objects.equals(balance, that.balance) &&
                currency == that.currency &&
                Objects.equals(userFK, that.userFK) &&
                Objects.equals(sent, that.sent) &&
                Objects.equals(received, that.received);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOpened, balance, currency, userFK, sent, received);
    }
}
