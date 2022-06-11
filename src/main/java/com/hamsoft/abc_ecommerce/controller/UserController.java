package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.dto.SignUpResponseDto;
import com.hamsoft.abc_ecommerce.dto.SignupDto;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.AuthenticationToken;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import com.hamsoft.abc_ecommerce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {

    final UserService userService;
    final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public SignUpResponseDto signUp(@Valid @RequestBody SignupDto signupDto) throws CustomException {
        if(userService.getUserByEmail(signupDto.getEmail()).isPresent()){
            throw new CustomException("User already exists");
        }
        userService.registerUser(signupDto);
        return new SignUpResponseDto("success", "user created successfully");
    }



}
