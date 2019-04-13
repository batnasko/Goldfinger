package com.goldfinger.auditability.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "logs")
public class Log {
    private static final String INVALID_USERNAME = "Username must be between 2 and 30 chars.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private int id;

    @Column(name = "username")
    @Size(min = 2, max = 30, message = INVALID_USERNAME)
    @NotNull
    private String username;

    @Column(name = "ip")
    @NotNull
    private String ip;

    @Column(name = "event_time")
    @NotNull
    private Timestamp eventTime;

    @OneToMany
    @JoinColumn(name = "log_id")
    private List<Pairs> keyValuePairs;
    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    public List<Pairs> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(List<Pairs> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }
}
