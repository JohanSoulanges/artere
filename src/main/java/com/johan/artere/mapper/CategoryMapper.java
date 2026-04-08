package com.johan.artere.mapper;

import com.johan.artere.dto.CategoryDto;
import com.johan.artere.dto.ProductDto;
import com.johan.artere.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final ProductMapper productMapper;

    public CategoryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CategoryDto.Response toResponse(Category category) {
        if (category == null) return null;

        CategoryDto.Response dto = new CategoryDto.Response();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setParentId(category.getParent() != null ? category.getParent().getId() : null);

        List<CategoryDto.Response> subDtos = category.getSubCategories()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        dto.setSubCategories(subDtos);

        List<ProductDto.Response> productDtos = category.getProducts()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
        dto.setProducts(productDtos);

        return dto;
    }

    public CategoryDto.Summary toSummary(Category category) {
        if (category == null) return null;

        CategoryDto.Summary dto = new CategoryDto.Summary();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setParentId(category.getParent() != null ? category.getParent().getId() : null);
        return dto;
    }
}
