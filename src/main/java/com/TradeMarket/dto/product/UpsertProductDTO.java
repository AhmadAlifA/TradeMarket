package com.TradeMarket.dto.product;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertProductDTO {

    private Long productId;

    @NotBlank(message = "Nama Product tidak boleh kosong")
    @Size(max = 50,message = "Nama Product tidak boleh lebih dari 50 characters.")
    private String productName;

    @NotNull(message = "harus ada Harga")
    @Min(value = 0, message = "Harga tidak boleh minus")
    @Digits(integer = 12, fraction = 2, message = "Desimal dengan 2 fractions.")
    private Double priceForBuy;

    @NotNull(message = "harus ada Harga")
    @Min(value = 0, message = "Harga tidak boleh minus")
    @Digits(integer = 12, fraction = 2, message = "Desimal dengan 2 fractions.")
    private Double priceForSell;

    @NotBlank(message = "Satuan tidak boleh kosong")
    private String satuan;

    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Size(max = 50,message = "Nama kategori tidak boleh lebih dari 50 characters.")
    private String category;
}
