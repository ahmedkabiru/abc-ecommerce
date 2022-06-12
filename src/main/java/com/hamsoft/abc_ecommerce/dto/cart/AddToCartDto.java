package com.hamsoft.abc_ecommerce.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddToCartDto {

    private Long id;
    private @NotNull Long productId;
    private @NotNull Integer quantity;

}
