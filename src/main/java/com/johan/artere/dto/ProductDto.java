package com.johan.artere.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

public class ProductDto {

    @Data
    public static class Request {
        @NotBlank(message = "Le nom est obligatoire")
        private String name;

        @NotNull(message = "Le prix est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
        private BigDecimal price;

        @NotNull(message = "La quantité en stock est obligatoire")
        @Min(value = 0, message = "Le stock ne peut pas être négatif")
        private Integer stockQuantity;
    }

    @Data
    public static class Response {
        private Long id;
        private String name;
        private BigDecimal price;
        private Integer stockQuantity;
        private Long categoryId;
        private String categoryName;
    }
}
