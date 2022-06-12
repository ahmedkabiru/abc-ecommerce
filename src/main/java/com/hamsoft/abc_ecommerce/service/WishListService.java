package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.model.WishList;

import java.util.List;

public interface WishListService {

    void createWishList(WishList wishList);

    List<WishList> getAllWishList(User user);
}
