package com.navarro.authenticationSystem.models.dto;


import com.navarro.authenticationSystem.models.User;

public record UserDTO(Long id, String name, String userName, String password) {

    public UserDTO(User user){
        this(user.getId(), user.getName(), user.getUserName(), user.getPassword());
    }
}
