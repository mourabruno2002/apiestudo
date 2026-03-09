package com.example.apiestudo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {

    //region FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String customerName;

    private String customerEmail;

    private String customerCpf;

    private String customerPhone;

    private String status;

    private BigDecimal shipping = BigDecimal.ZERO;

    private BigDecimal subtotal = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Version
    private Long version;
    //endregion

    //region GETTERS
    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public User getUser() {
        return user;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
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

    //region SETTERS
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //endregion

    //region DOMAIN METHODS
    public void recalculateTotals() {
        this.subtotal = BigDecimal.ZERO;
        BigDecimal itemDiscount = BigDecimal.ZERO;

        for (OrderItem item : items) {
            this.subtotal = this.subtotal.add(item.getSubtotal());
            itemDiscount = itemDiscount.add(item.getDiscount());
        }

        this.total = ((this.subtotal.add(this.shipping))
                .subtract(itemDiscount))
                .subtract(this.discount);
    }

    public void addItem(Product product, int quantity) {

        Optional<OrderItem> existingItem = items.stream().filter(item -> item.getProduct().getId().equals(
                product.getId())).findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().increaseQuantity(quantity);
        } else {
            OrderItem item = new OrderItem(product, quantity);
            addItemInternal(item);
        }

        recalculateTotals();
    }
    //endregion

    //region HELPER METHODS
    private void addItemInternal(OrderItem item) {
        item.setOrder(this);
        items.add(item);
    }
    //endregion

}
