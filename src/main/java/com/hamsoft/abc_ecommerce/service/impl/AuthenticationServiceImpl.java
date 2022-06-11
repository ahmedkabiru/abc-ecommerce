package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.commons.MessageStrings;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.AuthenticationToken;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.repository.TokenRepository;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    public User getUser(String token) {
        Optional<AuthenticationToken> authenticationToken=  tokenRepository.findByToken(token);
        return authenticationToken.map(AuthenticationToken::getUser).orElse(null);
    }

    @Override
    public void validateToken(String token) throws CustomException {
            if (StringUtils.isEmpty(token)) {
                throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
            }
            if (!Objects.nonNull(getUser(token))) {
                throw new CustomException(MessageStrings.AUTH_T0KEN_NOT_VALID);
            }
    }

}
