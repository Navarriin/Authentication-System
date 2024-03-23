package com.navarro.authenticationSystem.service.impl;

import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import com.navarro.authenticationSystem.models.dto.mapper.UserMapper;
import com.navarro.authenticationSystem.repository.UserRepository;
import com.navarro.authenticationSystem.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream().map(mapper::toDTO)
                .sorted(Comparator.comparing(UserDTO::id))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Long id) {
        return mapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id " + id + " not found!")));
    }

    @Override
    public UserDTO createUser(UserDTO body) {
        return null;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO body) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
