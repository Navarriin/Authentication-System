package com.navarro.authenticationSystem.service.impl;

import com.navarro.authenticationSystem.exceptions.ExistingUserException;
import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.exceptions.UnauthorizedException;
import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import com.navarro.authenticationSystem.models.dto.mapper.UserMapper;
import com.navarro.authenticationSystem.repository.UserRepository;
import com.navarro.authenticationSystem.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper mapper, UserRepository repository, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String longin(RequestLogin requestLogin) {
        Optional<User> byUserName = Optional.ofNullable(repository.findByUserName(requestLogin.username()));

        if(byUserName.isPresent()) {
            User user = byUserName.get();
            if(passwordEncoder.matches(requestLogin.password(), user.getPassword())) {
                return "Entrou(RETORNAR TOKEN GERADO)";
            } else {
                throw new UnauthorizedException("Incorrect password for user: " + requestLogin.username());
            }
        }
        throw new NotFoundException("Username " + requestLogin.username() + " not exist!");
    }

    @Override
    public UserDTO register(UserDTO body) {
        String encodePass = passwordEncoder.encode(body.password());
        User byUserName = repository.findByUserName(body.user_name());

        if(Objects.nonNull(byUserName)) throw new ExistingUserException();

        User user = new User(body.name(), body.user_name(), encodePass);
        repository.save(user);
        return mapper.toDTO(user);
    }
}
