package com.navarro.authenticationSystem.service;

import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;

import java.util.List;

public interface UserService {

    String login(RequestLogin requestLogin);
    String register(UserDTO body);

}
