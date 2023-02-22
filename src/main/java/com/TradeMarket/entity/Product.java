package com.TradeMarket.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "PriceForSell")
    private Double priceForSell;

    @Column(name = "PriceForBuy")
    private Double priceForBuy;

    @Column(name = "Satuan")
    private String satuan;

    @Column(name = "Category")
    private String category;


}
