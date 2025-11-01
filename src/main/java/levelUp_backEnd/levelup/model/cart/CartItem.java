package levelUp_backEnd.levelup.model.cart;

import jakarta.persistence.*;
import levelUp_backEnd.levelup.model.Cart;

import java.math.BigDecimal;

@Entity(name = "CartItemCart")
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer qty = 1;

    private String imageUrl;

    // Constructor sin-args requerido por JPA
    protected CartItem() {}

    // Constructor de conveniencia
    public CartItem(String productId, String name, BigDecimal price, Integer qty, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.qty = qty == null ? 1 : qty;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}