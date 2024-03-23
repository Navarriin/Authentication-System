package com.navarro.authenticationSystem.models.dto.mapper;

import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if(Objects.isNull(user)) return null;

        return new UserDTO(user);
    }

    public User toEntity(UserDTO userDTO) {
        if(Objects.isNull(userDTO)) return null;

        User user = new User();
        if(Objects.nonNull(userDTO.id())) user.setId(userDTO.id());

        user.setName(userDTO.name());
        user.setUserName(userDTO.userName());
        user.setPassword(userDTO.password());
        return user;
    }
}
