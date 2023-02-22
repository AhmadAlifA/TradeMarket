package com.TradeMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    @Column(name = "OrderDate")
    private LocalDate orderDate;

    @Column(name = "KonsumenName")
    private String konsumenName;

    @Column(name = "ProductId")
    private Long productId;

    @ManyToOne
    @JoinColumn(name="ProductId", insertable = false, updatable = false)
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "unitPrice")
    private Double unitPrice;

    public Order(Long orderId, LocalDate orderDate, String konsumenName, Long productId, Integer quantity, Double unitPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.konsumenName = konsumenName;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
