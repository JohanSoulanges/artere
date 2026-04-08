package com.johan.artere.mapper;

import com.johan.artere.dto.CartDto;
import com.johan.artere.model.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartDto.Response toResponse(Cart cart) {
        CartDto.Response dto = new CartDto.Response();
        dto.setId(cart.getId());
        dto.setItems(List.of());
        return dto;
    }
}
