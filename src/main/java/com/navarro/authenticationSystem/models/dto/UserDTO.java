package com.navarro.authenticationSystem.models.dto;


import com.navarro.authenticationSystem.models.User;

public record UserDTO(String name, String user_name, String password) {

    public UserDTO(User user){
        this(user.getName(), user.getUserName(), user.getPassword());
    }
}
