
package levelUp_backEnd.levelup.web.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDtos {

    public static class CartItemDto {
        public Long id;
        public String productId;
        public String name;
        public BigDecimal price;
        public Integer qty;
        public String imageUrl;
    }

    public static class CartResponse {
        public Long userId;
        public List<CartItemDto> items;
        public int totalItems;
        public BigDecimal subtotal;
    }

    public static class AddItemRequest {
        public String productId;
        public String name;
        public BigDecimal price;
        public Integer qty;
        public String imageUrl;
    }

    public static class UpdateQtyRequest {
        public Integer qty;
    }

    public static class ReplaceCartRequest {
        public List<AddItemRequest> items;
    }
}
