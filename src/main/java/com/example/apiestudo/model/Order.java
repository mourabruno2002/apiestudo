package com.example.apiestudo.model;

import com.example.apiestudo.enums.OrderStatus;
import com.example.apiestudo.exception.domain.order.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

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

    public OrderStatus getStatus() {
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
    //endregion

    //region DOMAIN METHODS
    protected Order(){}

    public static Order create(User user) {

        if (user == null) {
            throw new InvalidUserException("User is required.");
        }

        Order newOrder = new Order();

        newOrder.user = user;
        newOrder.copyCustomerSnapshot(user);
        newOrder.status = OrderStatus.CREATED;

        return newOrder;
    }

    public void addItem(Product product, int quantity) {
        assertOrderIsModifiable();

        if (quantity <= 0) {
            throw new InvalidQuantityException(String.format("Invalid quantity: %d", quantity));
        }

        Optional<OrderItem> existingItem = findOrderItemByProductId(product.getId());

        existingItem.ifPresentOrElse(
                item -> item.increaseQuantity(quantity), () -> addItemInternal(new OrderItem(product, quantity)));

        recalculateTotals();
    }

    public void removeItem(Long productId) {
        assertOrderIsModifiable();

        Optional<OrderItem> existingItem = findOrderItemByProductId(productId);

        existingItem.ifPresent(item -> this.items.remove(item));

        recalculateTotals();
    }

    public void increaseItemQuantity(Long productId, int amount) {
        withOrderItem(productId, item -> item.increaseQuantity(amount));
    }

    public void decreaseItemQuantity(Long productId, int amount) {
        withOrderItem(productId, item -> item.decreaseQuantity(amount));
    }

    public void applyDiscount(BigDecimal discount) {
        assertOrderIsModifiable();

        if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(this.subtotal.add(this.shipping)) > 0) {
            throw new InvalidDiscountException(String.format("Invalid discount. Discount: %s", discount));
        }

        this.discount = discount;

        recalculateTotals();
    }

    public void applyShipping(BigDecimal shipping) {
        assertOrderIsModifiable();

        if (shipping.compareTo(BigDecimal.ZERO) < 0) {
            throw  new InvalidShippingException(String.format("Invalid shipping. Shipping: %s", shipping));
        }

        this.shipping = shipping;

        recalculateTotals();
    }

    public void changeStatus(OrderStatus newStatus) {

        if (!this.status.canTransitionTo(newStatus)) {
            throw new InvalidStatusException(String.format("Invalid status transition: %s -> %s", this.status,
                    newStatus));
        }

        this.status = newStatus;
    }
    //endregion

    //region INTERNAL METHODS
    private void recalculateTotals() {
        this.subtotal = BigDecimal.ZERO;
        BigDecimal totalItems = BigDecimal.ZERO;

        for (OrderItem item : items) {
            this.subtotal = this.subtotal.add(item.getSubtotal());
            totalItems = totalItems.add(item.getTotal());
        }

        this.total = ((totalItems.add(this.shipping))
                .subtract(this.discount));
    }

    private void addItemInternal(OrderItem item) {
        item.setOrder(this);
        this.items.add(item);
    }

    private Optional<OrderItem> findOrderItemByProductId(Long productId) {

        return this.items.stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }
    
    private OrderItem getOrderItemByProductId(Long productId) {

        return this.items.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(
                () -> new OrderItemNotFoundException(String.format("OrderItem not found. ProductID: %d", productId))
        );
    }

    private void withOrderItem(Long productId, Consumer<OrderItem> action) {
        assertOrderIsModifiable();

        OrderItem item = getOrderItemByProductId(productId);
        action.accept(item);

        if (item.isEmpty()) {
            this.items.remove(item);
        }

        recalculateTotals();
    }

    private void copyCustomerSnapshot(User user) {
        this.customerName = user.getName();
        this.customerEmail = user.getUsername();
        this.customerCpf = user.getCpf();
        this.customerPhone = user.getPhoneNumber();
    }

    private void assertOrderIsModifiable() {
        if (this.status.isFinal()) {
            throw new InvalidStatusException(String.format("Order cannot be modified when the status is %s.", this.status));
        }
    }
    //endregion

}
