package com.hamsoft.abc_ecommerce.repository;

import com.hamsoft.abc_ecommerce.model.Cart;
import com.hamsoft.abc_ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
