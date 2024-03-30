package com.navarro.authenticationSystem.service;

import com.navarro.authenticationSystem.models.User;

public interface TokenService {

    String generateToken(User user);
    String validateToken(String token);
}
