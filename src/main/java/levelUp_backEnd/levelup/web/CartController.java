
package levelUp_backEnd.levelup.web;

import com.levelup.backend.model.Cart;
import com.levelup.backend.service.CartService;
import com.levelup.backend.web.dto.CartDtos.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public CartResponse get(@PathVariable Long userId) {
        Cart c = service.getOrCreate(userId);
        return CartMappers.toResponse(c);
    }

    @PostMapping("/{userId}/items")
    public CartResponse addItem(@PathVariable Long userId, @Valid @RequestBody AddItemRequest req) {
        Cart c = service.addItem(userId, req);
        return CartMappers.toResponse(c);
    }

    @PutMapping("/{userId}/items/{itemId}")
    public CartResponse updateQty(@PathVariable Long userId, @PathVariable Long itemId, @RequestBody UpdateQtyRequest req) {
        Cart c = service.updateQty(userId, itemId, req.qty);
        return CartMappers.toResponse(c);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public CartResponse removeItem(@PathVariable Long userId, @PathVariable Long itemId) {
        Cart c = service.removeItem(userId, itemId);
        return CartMappers.toResponse(c);
    }

    @DeleteMapping("/{userId}")
    public CartResponse clear(@PathVariable Long userId) {
        Cart c = service.clear(userId);
        return CartMappers.toResponse(c);
    }

    @PutMapping("/{userId}")
    public CartResponse replace(@PathVariable Long userId, @RequestBody ReplaceCartRequest req) {
        Cart c = service.replaceCart(userId, req);
        return CartMappers.toResponse(c);
    }
}
