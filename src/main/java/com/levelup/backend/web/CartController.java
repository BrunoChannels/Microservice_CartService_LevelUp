package com.levelup.backend.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levelup.backend.model.Cart;
import com.levelup.backend.service.CartService;
import com.levelup.backend.web.dto.CartDtos.AddItemRequest;
import com.levelup.backend.web.dto.CartDtos.CartResponse;
import com.levelup.backend.web.dto.CartDtos.ReplaceCartRequest;
import com.levelup.backend.web.dto.CartDtos.UpdateQtyRequest;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public CartResponse get(@PathVariable("userId") Long userId) {
        Cart c = service.getOrCreate(userId);
        return CartMappers.toResponse(c);
    }

    @PostMapping("/{userId}/items")
    public CartResponse addItem(@PathVariable("userId") Long userId,
                                @RequestBody AddItemRequest req) {
        Cart c = service.addItem(userId, req);
        return CartMappers.toResponse(c);
    }

    @PatchMapping("/{userId}/items/{itemId}")
    public CartResponse updateQty(@PathVariable("userId") Long userId,
                                  @PathVariable("itemId") Long itemId,
                                  @RequestBody UpdateQtyRequest req) {
        Cart c = service.updateQty(userId, itemId, req.qty);
        return CartMappers.toResponse(c);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public CartResponse removeItem(@PathVariable("userId") Long userId,
                                   @PathVariable("itemId") Long itemId) {
        Cart c = service.removeItem(userId, itemId);
        return CartMappers.toResponse(c);
    }

    @DeleteMapping("/{userId}")
    public CartResponse clear(@PathVariable("userId") Long userId) {
        Cart c = service.clear(userId);
        return CartMappers.toResponse(c);
    }

    @PutMapping("/{userId}")
    public CartResponse replace(@PathVariable("userId") Long userId,
                                @RequestBody ReplaceCartRequest req) {
        Cart c = service.replaceCart(userId, req);
        return CartMappers.toResponse(c);
    }
}
