package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.model.AuthenticationToken;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.repository.TokenRepository;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {

    final TokenRepository tokenRepository;

    @Override
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    @Override
    public Optional<AuthenticationToken> getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public Optional<User> getUser(String token) {
        Optional<AuthenticationToken> authenticationToken=  tokenRepository.findByToken(token);
        return authenticationToken.map(value -> Optional.of(value.getUser())).orElse(null);
    }
}
