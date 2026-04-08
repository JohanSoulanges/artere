package com.johan.artere.mapper;

import com.johan.artere.dto.ProductDto;
import com.johan.artere.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto.Response toResponse(Product product) {
        if (product == null) return null;

        ProductDto.Response dto = new ProductDto.Response();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }
}
