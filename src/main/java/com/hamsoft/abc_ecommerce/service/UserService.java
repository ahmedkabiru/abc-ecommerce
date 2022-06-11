package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.SignUpResponseDto;
import com.hamsoft.abc_ecommerce.dto.SignupDto;
import com.hamsoft.abc_ecommerce.model.User;

import java.util.Optional;

public interface UserService {


    void registerUser(SignupDto signupDto);

    Optional<User> getUserByEmail(String email);
}
