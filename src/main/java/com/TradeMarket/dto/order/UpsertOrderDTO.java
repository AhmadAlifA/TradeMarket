package com.TradeMarket.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertOrderDTO {

    private Long orderId;

    private LocalDate orderDate;

    @NotBlank(message = "Konsumen Name tidak boleh kosong")
    @Size(max = 50,message = "Nama Product tidak boleh lebih dari 50 characters.")
    private String konsumenName;

    private Long productId;

    @NotNull(message = "Quantity tidak boleh kosong")
    private Integer quantity;

    private Double unitPrice;
}
