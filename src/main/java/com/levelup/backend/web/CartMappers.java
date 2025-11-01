package com.levelup.backend.web;

import com.levelup.backend.model.Cart;
import com.levelup.backend.model.CartItem;
import com.levelup.backend.web.dto.CartDtos.CartItemDto;
import com.levelup.backend.web.dto.CartDtos.CartResponse;

public class CartMappers {
  public static CartItemDto toDto(CartItem it) {
    CartItemDto d = new CartItemDto();
    d.id = it.getId(); d.productId = it.getProductId(); d.name = it.getName();
    d.price = it.getPrice(); d.qty = it.getQty(); d.imageUrl = it.getImageUrl();
    return d;
  }
  public static CartResponse toResponse(Cart cart) {
    CartResponse r = new CartResponse();
    r.userId = cart.getUserId();
    r.items = cart.getItems().stream().map(CartMappers::toDto).toList();
    r.totalItems = cart.getItems().stream().mapToInt(ci -> ci.getQty()==null?0:ci.getQty()).sum();
    r.subtotal = cart.getItems().stream()
      .map(ci -> ci.getPrice().multiply(java.math.BigDecimal.valueOf(ci.getQty())))
      .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    return r;
  }
}
