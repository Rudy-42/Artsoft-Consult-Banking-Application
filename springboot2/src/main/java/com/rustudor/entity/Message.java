package com.rustudor.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table
public class Message {
    @Id
    @GeneratedValue
    @Column(nullable = false,updatable = false)
    private int id;
    private String message;
    private String title;
    private String adminName;
    private Timestamp sentTime;
    @ManyToOne
    @NotNull
    private User userFK;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public User getUserFK() {
        return userFK;
    }

    public void setUserFK(User userFK) {
        this.userFK = userFK;
    }
}
