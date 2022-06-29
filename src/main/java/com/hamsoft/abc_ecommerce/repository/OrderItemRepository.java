package com.hamsoft.abc_ecommerce.repository;

import com.hamsoft.abc_ecommerce.model.Order;
import com.hamsoft.abc_ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
