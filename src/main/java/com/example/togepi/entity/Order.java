package com.example.togepi.entity;

import com.example.togepi.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "status", length = 10, nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @NotEmpty
    @JsonManagedReference
    private List<Item> items;

    @Transient
    private BigDecimal priceSubtotal;

    @Transient
    private BigDecimal taxSubtotal;

    @Transient
    private BigDecimal grandTotal;

    public Order() {
        this.priceSubtotal = this.taxSubtotal = this.grandTotal = BigDecimal.ZERO;
    }

    public Order(List<Item> items) {
        this.priceSubtotal = this.taxSubtotal = this.grandTotal = BigDecimal.ZERO;
        this.items = items;
    }
}
