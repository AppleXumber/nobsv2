package com.applexumber.nobsv2.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customUser")
public class CustomUser {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public CustomUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CustomUser() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
