package com.navarro.authenticationSystem.models;

import com.navarro.authenticationSystem.models.dto.UserDTO;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
