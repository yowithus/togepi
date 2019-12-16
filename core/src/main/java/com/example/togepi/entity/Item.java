package com.example.togepi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    @NotBlank
    private String name;

    @Column(name = "category_id", length = 2, nullable = false)
    @NotBlank
    private String categoryId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Transient
    private String categoryName;

    @Transient
    private BigDecimal tax;

    @Transient
    private BigDecimal amount;

    public Item(String name, String categoryId, BigDecimal price) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
    }
}
