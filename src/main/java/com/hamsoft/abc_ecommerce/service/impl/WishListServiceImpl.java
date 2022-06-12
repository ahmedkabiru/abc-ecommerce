package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.model.WishList;
import com.hamsoft.abc_ecommerce.repository.WishListRepository;
import com.hamsoft.abc_ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    final WishListRepository wishListRepository;

    @Override
    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    @Override
    public List<WishList> getAllWishList(User user) {
        return wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
}
