package com.hamsoft.abc_ecommerce.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutItemDto {

    private String productName;
    private int quantity;
    private double price;
    private long productId;
}
