package com.navarro.authenticationSystem.controller;

import com.navarro.authenticationSystem.models.dto.RequestLogin;
import com.navarro.authenticationSystem.models.dto.ResponseWithToken;
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

    @GetMapping("test")
    public ResponseEntity<String> testToken(){
        return ResponseEntity.ok().body("Success!");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWithToken> login(@RequestBody RequestLogin requestLogin){
        return ResponseEntity.ok()
                .body(new ResponseWithToken(requestLogin.username(), this.service.login(requestLogin)));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWithToken> register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok()
                .body(new ResponseWithToken(userDTO.user_name(), this.service.register(userDTO)));
    }

}
