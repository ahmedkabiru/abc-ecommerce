package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.SignInDto;
import com.hamsoft.abc_ecommerce.dto.SignUpResponseDto;
import com.hamsoft.abc_ecommerce.dto.SignupDto;
import com.hamsoft.abc_ecommerce.exceptions.AuthenticationFailException;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.User;

import java.util.Optional;

public interface UserService {


    void registerUser(SignupDto signupDto);

    String authenticateUser(SignInDto signInDto,User user) throws AuthenticationFailException, CustomException;

    Optional<User> getUserByEmail(String email);
}
