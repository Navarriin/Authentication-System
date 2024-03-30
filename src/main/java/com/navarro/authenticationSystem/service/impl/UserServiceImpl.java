package com.navarro.authenticationSystem.service.impl;

import com.navarro.authenticationSystem.exceptions.ExistingUserException;
import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.exceptions.UnauthorizedException;
import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import com.navarro.authenticationSystem.repository.UserRepository;
import com.navarro.authenticationSystem.service.TokenService;
import com.navarro.authenticationSystem.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(RequestLogin requestLogin) {
        User user = this.repository.findByUserName(requestLogin.username())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        if(this.passwordEncoder.matches(requestLogin.password(), user.getPassword())) {
            return this.tokenService.generateToken(user);
        } else {
            throw new UnauthorizedException("Incorrect password for user: " + requestLogin.username());
        }
    }

    @Override
    public String register(UserDTO body) {
        Optional<User> byUserName = this.repository.findByUserName(body.user_name());

        if(byUserName.isEmpty()) {
            return this.tokenService.generateToken(createNewUser(body));
        }
        throw new ExistingUserException();
    }

    public User createNewUser(UserDTO body) {
        String encodePass = this.passwordEncoder.encode(body.password());
        return this.repository.save(new User(body.name(), body.user_name(), encodePass));
    }
}
