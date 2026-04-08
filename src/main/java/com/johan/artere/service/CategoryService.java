package com.johan.artere.service;

import com.johan.artere.dto.CategoryDto;
import com.johan.artere.exception.ResourceNotFoundException;
import com.johan.artere.mapper.CategoryMapper;
import com.johan.artere.model.Category;
import com.johan.artere.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto.Summary> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toSummary)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDto.Response> getRootCategories() {
        return categoryRepository.findByParentIsNull()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto.Response getCategoryById(Long id) {
        return categoryMapper.toResponse(findOrThrow(id));
    }

    public CategoryDto.Response createCategory(CategoryDto.Request request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        if (request.getParentId() != null) {
            category.setParent(findOrThrow(request.getParentId()));
        }

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    public CategoryDto.Response updateCategory(Long id, CategoryDto.Request request) {
        Category category = findOrThrow(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        if (request.getParentId() != null) {
            if (request.getParentId().equals(id)) {
                throw new IllegalArgumentException("Une catégorie ne peut pas être son propre parent");
            }
            category.setParent(findOrThrow(request.getParentId()));
        } else {
            category.setParent(null);
        }

        return categoryMapper.toResponse(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.delete(findOrThrow(id));
    }

    public CategoryDto.Response linkParent(Long categoryId, Long parentId) {
        if (categoryId.equals(parentId)) {
            throw new IllegalArgumentException("Une catégorie ne peut pas être son propre parent");
        }
        Category category = findOrThrow(categoryId);
        category.setParent(findOrThrow(parentId));
        return categoryMapper.toResponse(category);
    }

    public CategoryDto.Response unlinkParent(Long categoryId) {
        Category category = findOrThrow(categoryId);
        category.setParent(null);
        return categoryMapper.toResponse(category);
    }

    public Category findOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.category(id));
    }
}
