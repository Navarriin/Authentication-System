package com.navarro.authenticationSystem.service.impl;

import com.navarro.authenticationSystem.exceptions.ExistingUserException;
import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.exceptions.UnauthorizedException;
import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import com.navarro.authenticationSystem.repository.UserRepository;
import com.navarro.authenticationSystem.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private RequestLogin requestLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.user = new User("Test", "UsernameTest", "1234");
        this.userDTO = new UserDTO("Test", "UsernameTest", "1234");
        this.requestLogin = new RequestLogin("UsernameTest", "1234");
    }

    @Test
    void loginSuccess() {
        when(this.repository.findByUserName(this.requestLogin.username()))
                .thenReturn(Optional.ofNullable(this.user));
        when(this.passwordEncoder.matches(this.requestLogin.password(), this.user.getPassword())).thenReturn(true);

        assertDoesNotThrow(() -> this.userService.login(this.requestLogin));
        verify(this.tokenService, times(1)).generateToken(this.user);
    }

    @Test
    void loginNotFound() {
        when(this.repository.findByUserName(this.requestLogin.username())).thenReturn(Optional.empty());

        var result = assertThrows(NotFoundException.class, () -> this.userService.login(this.requestLogin));
        assertEquals("User " + this.requestLogin.username() + " not found!", result.getMessage());
    }

    @Test
    void loginUnauthorized(){
        when(this.repository.findByUserName(this.requestLogin.username()))
                .thenReturn(Optional.ofNullable(this.user));
        when(this.passwordEncoder.matches(this.requestLogin.password(), this.user.getPassword())).thenReturn(false);

        var result = assertThrows(UnauthorizedException.class, () -> this.userService.login(requestLogin));
        assertEquals("Incorrect password for user: " + this.requestLogin.username(), result.getMessage());
    }

    @Test
    void registerUserAlreadyExist() {
        when(this.repository.findByUserName(this.user.getUserName())).thenReturn(Optional.ofNullable(this.user));

        var result = assertThrows(ExistingUserException.class,() -> this.userService.register(this.userDTO));
        assertEquals("User " + this.user.getUserName() + " already exists!", result.getMessage());
    }

    @Test
    void registerSuccess() {
        when(this.repository.findByUserName(this.user.getUserName())).thenReturn(Optional.empty());

       assertDoesNotThrow(() -> this.userService.register(this.userDTO));
       verify(this.tokenService, times(1))
                .generateToken(this.userService.createNewUser(this.userDTO));
    }

}