package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.dto.user.SignInDto;
import com.hamsoft.abc_ecommerce.dto.user.SignInResponseDto;
import com.hamsoft.abc_ecommerce.dto.cart.SignUpResponseDto;
import com.hamsoft.abc_ecommerce.dto.cart.SignupDto;
import com.hamsoft.abc_ecommerce.exceptions.AuthenticationFailException;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import com.hamsoft.abc_ecommerce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

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


    @PostMapping("/login")
    public SignInResponseDto login(@Valid @RequestBody SignInDto signInDto) throws AuthenticationFailException, CustomException {
       Optional<User> user = userService.getUserByEmail(signInDto.getEmail());
        if(user.isEmpty()){
            throw new AuthenticationFailException("user not present");
        }
        String token = userService.authenticateUser(signInDto,user.get());
        return new SignInResponseDto ("success", token);
    }





}
