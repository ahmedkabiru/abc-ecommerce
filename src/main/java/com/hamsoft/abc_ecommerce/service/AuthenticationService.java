package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.AuthenticationToken;
import com.hamsoft.abc_ecommerce.model.User;

import java.util.Optional;

public interface AuthenticationService {

    void  saveConfirmationToken(AuthenticationToken authenticationToken);

    Optional<AuthenticationToken> getToken(User user);

    User getUser(String  token);

    void validateToken(String token) throws CustomException;




}
