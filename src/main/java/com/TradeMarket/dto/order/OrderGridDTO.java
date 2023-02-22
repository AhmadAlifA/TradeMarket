package com.TradeMarket.dto.order;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderGridDTO {
    private Long orderId;
    private LocalDate orderDate;
    private String konsumenName;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    public OrderGridDTO(Long orderId, LocalDate orderDate, String konsumenName, Long productId, Integer quantity, Double unitPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.konsumenName = konsumenName;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = (quantity * unitPrice);
    }
}
