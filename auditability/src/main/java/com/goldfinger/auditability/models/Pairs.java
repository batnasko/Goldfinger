package com.goldfinger.auditability.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pairs")
public class Pairs {

    private static final String NOT_VALID_KEY = "Please enter valid key! Key must be between 2 and 50 characters!";
    private static final String NOT_VALID_VALUE = "Please enter valid value! Value must be between 2 and 50 characters!";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "log_id")
    @NotNull
    private int logId;

    @Column(name = "key_")
    @NotNull
    @Size(min = 2, max = 50, message = NOT_VALID_KEY)
    private String key;

    @Column(name = "value_")
    @NotNull
    @Size(min = 2, max = 50, message = NOT_VALID_VALUE)
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
