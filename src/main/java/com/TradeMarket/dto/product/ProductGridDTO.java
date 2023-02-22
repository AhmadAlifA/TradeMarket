package com.TradeMarket.dto.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductGridDTO {

    private Long productId;

    private String productName;

    private Double priceForBuy;

    private Double priceForSell;

    private String satuan;

    private String category;
}