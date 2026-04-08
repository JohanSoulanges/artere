package com.johan.artere.service;

import com.johan.artere.dto.ProductDto;
import com.johan.artere.exception.ResourceNotFoundException;
import com.johan.artere.model.Product;
import com.johan.artere.repository.ProductRepository;
import com.johan.artere.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDto.Response> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto.Response getProductById(Long id) {
        return productMapper.toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<ProductDto.Response> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductDto.Response createProduct(ProductDto.Request request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductDto.Response updateProduct(Long id, ProductDto.Request request) {
        Product product = findOrThrow(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        return productMapper.toResponse(product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(findOrThrow(id));
    }

    public ProductDto.Response linkToCategory(Long productId, Long categoryId) {
        Product product = findOrThrow(productId);
        product.setCategory(categoryService.findOrThrow(categoryId));
        return productMapper.toResponse(product);
    }

    public ProductDto.Response unlinkFromCategory(Long productId) {
        Product product = findOrThrow(productId);
        product.setCategory(null);
        return productMapper.toResponse(product);
    }

    private Product findOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.product(id));
    }
}
