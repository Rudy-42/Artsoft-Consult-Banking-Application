package com.rustudor.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table
public class Appointment {
    @Id
    @GeneratedValue
    @Column(nullable = false,updatable = false)
    private int id;
    @NotNull
    private Timestamp timeFrom;
    @NotNull
    private Timestamp timeTo;
    @ManyToOne
    @NotNull
    private User userFK;
    private String adminName;




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


    public Timestamp getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Timestamp timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Timestamp getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Timestamp timeTo) {
        this.timeTo = timeTo;
    }

    public User getUserFK() {
        return userFK;
    }

    public void setUserFK(User userFK) {
        this.userFK = userFK;
    }
}
