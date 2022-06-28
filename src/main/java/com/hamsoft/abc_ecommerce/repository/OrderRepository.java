package com.hamsoft.abc_ecommerce.repository;

import com.hamsoft.abc_ecommerce.model.Order;
import com.hamsoft.abc_ecommerce.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
