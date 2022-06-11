package com.hamsoft.abc_ecommerce.repository;

import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
