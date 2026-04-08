package com.johan.artere.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

public class CategoryDto {

    @Data
    public static class Request {
        @NotBlank(message = "Le nom est obligatoire")
        private String name;
        private String description;
        private Long parentId;
    }

    @Data
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private Long parentId;
        private List<Response> subCategories;
        private List<ProductDto.Response> products;
    }

    @Data
    public static class Summary {
        private Long id;
        private String name;
        private String description;
        private Long parentId;
    }
}
