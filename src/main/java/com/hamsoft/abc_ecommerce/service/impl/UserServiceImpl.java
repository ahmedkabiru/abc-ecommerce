package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.dto.SignUpResponseDto;
import com.hamsoft.abc_ecommerce.dto.SignupDto;
import com.hamsoft.abc_ecommerce.model.AuthenticationToken;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.repository.UserRepository;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import com.hamsoft.abc_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    final AuthenticationService authenticationService;

    @Override
    public void registerUser(SignupDto signupDto) {
        String encryptedPassword = passwordEncoder.encode(signupDto.getPassword());
        User newUser = new User();
        newUser.setEmail(signupDto.getEmail());
        newUser.setFirstName(signupDto.getFirstName());
        newUser.setLastName(signupDto.getLastName());
        newUser.setPassword(encryptedPassword);
        User user = userRepository.save(newUser);
        AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
