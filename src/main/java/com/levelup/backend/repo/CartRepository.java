package com.levelup.backend.repo;

import com.levelup.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUserId(Long userId);
  boolean existsByUserId(Long userId);
}
