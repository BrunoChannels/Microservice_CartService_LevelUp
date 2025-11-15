package com.levelup.backend.service;

import com.levelup.backend.model.Cart;
import com.levelup.backend.model.CartItem;
import com.levelup.backend.repo.CartRepository;
import com.levelup.backend.web.dto.CartDtos.AddItemRequest;
import com.levelup.backend.web.dto.CartDtos.ReplaceCartRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {

    private final CartRepository cartRepo;

    public CartService(CartRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Transactional
    public Cart getOrCreate(Long userId) {
        return cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUserId(userId);
                    return cartRepo.save(c);
                });
    }

    @Transactional
    public Cart addItem(Long userId, AddItemRequest req) {
        Cart cart = getOrCreate(userId);

        // Busca si ya existe el item del mismo producto
        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(req.productId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQty(existing.getQty() + (req.qty != null ? req.qty : 1));
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductId(req.productId);
            item.setName(req.name);
            item.setPrice(req.price != null ? req.price : BigDecimal.ZERO);
            item.setQty(req.qty != null ? req.qty : 1);
            item.setImageUrl(req.imageUrl);
            cart.getItems().add(item);
        }

        return cartRepo.save(cart);
    }

    @Transactional
    public Cart updateQty(Long userId, Long itemId, Integer qty) {
        Cart cart = getOrCreate(userId);

        final CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(null);

        item.setQty(qty);
        return cartRepo.save(cart);
    }

    @Transactional
    public Cart removeItem(Long userId, Long itemId) {
        Cart cart = getOrCreate(userId);
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepo.save(cart);
    }

    @Transactional
    public Cart clear(Long userId) {
        Cart cart = getOrCreate(userId);
        cart.getItems().clear();
        return cartRepo.save(cart);
    }

    @Transactional
    public Cart replaceCart(Long userId, ReplaceCartRequest req) {
        Cart cart = getOrCreate(userId);
        cart.getItems().clear();

        if (req.items != null) {
            req.items.forEach(i -> addItem(userId, i));
        }

        return cartRepo.findByUserId(userId)
        .orElseThrow(() -> new IllegalStateException("No se pudo encontrar el carrito para el usuario " + userId));

    }
}
