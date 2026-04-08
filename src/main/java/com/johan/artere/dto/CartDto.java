package com.johan.artere.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {

    @Data
    @Schema(name = "CartItemResponse")
    public static class ItemResponse {
        @Schema(example = "1")
        private Long id;

        @Schema(example = "1")
        private Long productId;

        @Schema(example = "iPhone 15 Pro")
        private String productName;

        @Schema(example = "1199.99")
        private BigDecimal unitPrice;

        @Schema(example = "2")
        private Integer quantity;

        @Schema(example = "2399.98")
        private BigDecimal subTotal;
    }

    @Data
    @Schema(name = "CartResponse")
    public static class Response {
        @Schema(example = "1")
        private Long id;

        private List<ItemResponse> items;

        @Schema(example = "2399.98")
        private BigDecimal total;
    }
}
