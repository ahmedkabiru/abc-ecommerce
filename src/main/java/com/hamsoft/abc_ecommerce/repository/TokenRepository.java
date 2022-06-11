package com.hamsoft.abc_ecommerce.repository;

import com.hamsoft.abc_ecommerce.model.AuthenticationToken;
import com.hamsoft.abc_ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {

    Optional<AuthenticationToken> findByUser(User user);
    Optional<AuthenticationToken> findByToken(String token);
}
