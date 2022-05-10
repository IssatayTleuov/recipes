package com.example.recipes.presentation;

import com.example.recipes.businesslayer.User;
import com.example.recipes.businesslayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private final UserService userService;

    public RegisterController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
