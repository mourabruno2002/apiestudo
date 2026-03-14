package com.example.apiestudo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String SKU;

    private BigDecimal unitPrice;

    private int quantity;

    private BigDecimal discountPerUnit = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Version
    private Long version;

    public OrderItem(){};

    protected OrderItem(Product product, int quantity) {
        this.productName = product.getName();
        this.SKU = product.getSku();
        this.unitPrice = product.getPrice();
        this.product = product;
        this.quantity = quantity;
        getSubtotal();
    }


    //region GETTER
    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getSKU() {
        return SKU;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscountPerUnit() {
        return discountPerUnit;
    }

    public BigDecimal getSubtotal() {
        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
    //endregion

    //region SETTER
    public void setOrder(Order order) {
        this.order = order;
    }
    //endregion

    //DOMAIN METHODS
    protected boolean isEmpty() {
        return this.quantity <= 0;
    }

    protected void increaseQuantity(Integer amount) {
        quantity += amount;
    }

    protected void decreaseQuantity(Integer amount) {
        quantity -= amount;
    }

    protected BigDecimal getItemDiscount() {
        return this.discountPerUnit.multiply(BigDecimal.valueOf(this.quantity));
    }

    protected BigDecimal getTotal() {

        return getSubtotal().subtract(getItemDiscount());
    }
}
