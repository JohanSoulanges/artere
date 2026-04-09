package com.johan.artere.mapper;

import com.johan.artere.dto.CartDto;
import com.johan.artere.model.Cart;
import com.johan.artere.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDto.Response toResponse(Cart cart) {
        CartDto.Response dto = new CartDto.Response();
        dto.setId(cart.getId());
        dto.setItems(cart.getItems().stream().map(this::toItemResponse).collect(Collectors.toList()));
        dto.setTotal(cart.getTotal());
        return dto;
    }

    public CartDto.ItemResponse toItemResponse(CartItem item) {
        CartDto.ItemResponse dto = new CartDto.ItemResponse();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setUnitPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubTotal(item.getSubTotal());
        return dto;
    }
}
