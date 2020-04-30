package com.rustudor.entity;


import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(nullable = false,updatable = false)
    private int id;
    //@NotNull
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    //@NotNull
    private String cnp;
    @OneToMany(mappedBy = "userFK")
    private List<Account> accounts = new ArrayList<Account>();
    @OneToOne
    @NotNull
    private Login login;
    @OneToMany(mappedBy = "userFK")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "userFK")
    private Set<Message> messages;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }


    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
