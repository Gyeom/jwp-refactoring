package kitchenpos.product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    private Product(Long id, String name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product of(Long id, String name, BigDecimal price) {
        return new Product(id, name, new ProductPrice(price));
    }

    public BigDecimal multiplyQuantity(BigDecimal quantity) {
        return price.multiply(quantity);
    }

    public boolean equalsId(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ProductPrice getPrice() {
        return price;
    }
}