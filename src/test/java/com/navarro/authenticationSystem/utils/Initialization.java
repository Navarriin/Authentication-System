package com.navarro.authenticationSystem.utils;

import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;

public class Initialization {

    public static User initUser(){
        return new User("Test", "UsernameTest", "1234");
    }

    public static UserDTO initUserDTO(){
        return new UserDTO("Test", "UsernameTest", "1234");
    }

    public static RequestLogin initRequest(){
        return new RequestLogin("UsernameTest", "1234");
    }
}
