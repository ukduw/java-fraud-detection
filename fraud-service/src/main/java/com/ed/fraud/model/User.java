package com.ed.fraud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String name;
    private String homeLocation;

    public User() {}

    public User(String userId, String name, String homeLocation) {
        this.userId = userId;
        this.name = name;
        this.homeLocation = homeLocation;
    }

    // getters/setters...
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getHomeLocation() { return homeLocation; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setName(String name) { this.name = name;}
    public void setHomeLocation(String homeLocation) { this.homeLocation = homeLocation; }
}
