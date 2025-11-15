package com.levelup.backend.web;

import com.levelup.backend.model.Cart;
import com.levelup.backend.model.CartItem;
import com.levelup.backend.web.dto.CartDtos.CartItemDto;
import com.levelup.backend.web.dto.CartDtos.CartResponse;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class CartMappers {

    public static CartItemDto toDto(CartItem it) {
        CartItemDto d = new CartItemDto();
        d.id = it.getId();
        d.productId = it.getProductId();
        d.name = it.getName();
        d.price = it.getPrice();
        d.qty = it.getQty();
        d.imageUrl = it.getImageUrl();
        return d;
    }

    public static CartResponse toResponse(Cart cart) {
        CartResponse r = new CartResponse();

        // 👈 vínculo solo por userId
        r.userId = cart.getUserId();

        // items
        r.items = cart.getItems()
                .stream()
                .map(CartMappers::toDto)
                .collect(Collectors.toList());

        // total de ítems (si qty viene null, cuenta 0)
        r.totalItems = cart.getItems().stream()
                .mapToInt(ci -> ci.getQty() == null ? 0 : ci.getQty())
                .sum();

        // subtotal protegido contra null en price/qty
        r.subtotal = cart.getItems().stream()
                .map(ci -> {
                    BigDecimal price = ci.getPrice() != null ? ci.getPrice() : BigDecimal.ZERO;
                    int qty = ci.getQty() != null ? ci.getQty() : 0;
                    return price.multiply(BigDecimal.valueOf(qty));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return r;
    }
}
