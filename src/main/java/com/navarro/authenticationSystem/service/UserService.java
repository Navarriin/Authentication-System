package com.navarro.authenticationSystem.service;

import com.navarro.authenticationSystem.models.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO getById(Long id);
    UserDTO createUser(UserDTO body);
    UserDTO updateUser(Long id, UserDTO body);
    void deleteUser(Long id);
}
