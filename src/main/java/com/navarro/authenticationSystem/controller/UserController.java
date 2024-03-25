package com.navarro.authenticationSystem.controller;

import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.UserDTO;
import com.navarro.authenticationSystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestLogin requestLogin){
        return ResponseEntity.ok().body(service.longin(requestLogin));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(service.register(userDTO));
    }

}
