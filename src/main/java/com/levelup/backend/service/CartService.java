package com.levelup.backend.service;

import com.levelup.backend.model.Cart;
import com.levelup.backend.model.CartItem;
import com.levelup.backend.repo.CartItemRepository;
import com.levelup.backend.repo.CartRepository;
import com.levelup.backend.web.dto.CartDtos.AddItemRequest;
import com.levelup.backend.web.dto.CartDtos.ReplaceCartRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {
  private final CartRepository cartRepo;
  private final CartItemRepository itemRepo;
  public CartService(CartRepository cartRepo, CartItemRepository itemRepo) {
    this.cartRepo = cartRepo; this.itemRepo = itemRepo;
  }

  @Transactional
  public Cart getOrCreate(Long userId) {
    return cartRepo.findByUserId(userId).orElseGet(() -> {
      Cart c = new Cart(); c.setUserId(userId); return cartRepo.save(c);
    });
  }

  @Transactional
  public Cart addItem(Long userId, AddItemRequest req) {
    if (req.qty == null || req.qty <= 0) req.qty = 1;
    if (req.price == null) req.price = BigDecimal.ZERO;
    Cart cart = getOrCreate(userId);
    CartItem existing = cart.getItems().stream()
      .filter(i -> i.getProductId().equals(req.productId)).findFirst().orElse(null);
    if (existing != null) { existing.setQty(existing.getQty() + req.qty); }
    else {
      CartItem it = new CartItem();
      it.setCart(cart); it.setProductId(req.productId); it.setName(req.name);
      it.setPrice(req.price); it.setQty(req.qty); it.setImageUrl(req.imageUrl);
      cart.getItems().add(it);
    }
    return cartRepo.save(cart);
  }

  @Transactional
  public Cart updateQty(Long userId, Long itemId, int qty) {
    Cart cart = getOrCreate(userId);
    CartItem it = cart.getItems().stream().filter(i -> i.getId().equals(itemId))
      .findFirst().orElseThrow();
    if (qty <= 0) { cart.getItems().remove(it); itemRepo.delete(it); }
    else { it.setQty(qty); }
    return cartRepo.save(cart);
  }

  @Transactional
  public Cart removeItem(Long userId, Long itemId) {
    Cart cart = getOrCreate(userId);
    CartItem it = cart.getItems().stream().filter(i -> i.getId().equals(itemId))
      .findFirst().orElseThrow();
    cart.getItems().remove(it); itemRepo.delete(it);
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
    if (req.items != null) { req.items.forEach(i -> addItem(userId, i)); }
    return cartRepo.findByUserId(userId).orElseThrow();
  }
}
