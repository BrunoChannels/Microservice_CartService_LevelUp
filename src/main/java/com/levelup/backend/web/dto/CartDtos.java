package com.levelup.backend.web.dto;

import java.math.BigDecimal;

public class CartDtos {
  public static class CartItemDto {
    public Long id; public String productId; public String name;
    public BigDecimal price; public Integer qty; public String imageUrl;
  }
  public static class CartResponse {
    public Long userId;
    public java.util.List<CartItemDto> items;
    public int totalItems;
    public BigDecimal subtotal;
  }
  public static class AddItemRequest {
    public String productId; public String name; public BigDecimal price;
    public Integer qty; public String imageUrl;
  }
  public static class UpdateQtyRequest { public Integer qty; }
  public static class ReplaceCartRequest { public java.util.List<AddItemRequest> items; }
}
