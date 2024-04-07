package com.navarro.authenticationSystem.service;

import com.navarro.authenticationSystem.exceptions.ExistingUserException;
import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.exceptions.UnauthorizedException;
import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import com.navarro.authenticationSystem.repository.UserRepository;
import com.navarro.authenticationSystem.serviceImpl.UserServiceImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.navarro.authenticationSystem.utils.Initialization.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private static User user;
    private static UserDTO userDTO;
    private static RequestLogin request;

    @BeforeAll
    static void setUp() {
        user = initUser();
        userDTO = initUserDTO();
        request = initRequest();
    }

    @Test
    void loginSuccess() {
        when(this.repository.findByUserName(request.username())).thenReturn(Optional.of(user));
        when(this.passwordEncoder.matches(request.password(), user.getPassword())).thenReturn(true);

        assertDoesNotThrow(() -> this.userService.login(request));
        verify(this.tokenService, times(1)).generateToken(user);
    }

    @Test
    void loginNotFound() {
        when(this.repository.findByUserName(request.username())).thenReturn(Optional.empty());

        var result = assertThrows(NotFoundException.class, () -> this.userService.login(request));
        assertEquals("User " + request.username() + " not found!", result.getMessage());
    }

    @Test
    void loginUnauthorized(){
        when(this.repository.findByUserName(request.username())).thenReturn(Optional.of(user));
        when(this.passwordEncoder.matches(request.password(), user.getPassword())).thenReturn(false);

        var result = assertThrows(UnauthorizedException.class, () -> this.userService.login(request));
        assertEquals("Incorrect password for user: " + request.username(), result.getMessage());
    }

    @Test
    void registerUserAlreadyExist() {
        when(this.repository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));

        var result = assertThrows(ExistingUserException.class,() -> this.userService.register(userDTO));
        assertEquals("User " + user.getUserName() + " already exists!", result.getMessage());
    }

    @Test
    void registerSuccess() {
        when(this.repository.findByUserName(user.getUserName())).thenReturn(Optional.empty());

       assertDoesNotThrow(() -> this.userService.register(userDTO));
       verify(this.tokenService, times(1))
               .generateToken(this.userService.createNewUser(userDTO));
    }
}