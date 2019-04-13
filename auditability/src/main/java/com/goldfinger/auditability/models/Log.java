package com.goldfinger.auditability.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private int id;

    @JoinColumn(name = "message")
    @NotNull
    private String message;

    @OneToMany
    @JoinColumn(name = "log_id")
    private List<Pairs> keyValuePairs;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pairs> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(List<Pairs> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }
}
