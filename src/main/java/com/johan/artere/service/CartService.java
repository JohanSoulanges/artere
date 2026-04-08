package com.johan.artere.service;

import com.johan.artere.dto.CartDto;
import com.johan.artere.exception.ResourceNotFoundException;
import com.johan.artere.mapper.CartMapper;
import com.johan.artere.model.Cart;
import com.johan.artere.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    public CartDto.Response createCart() {
        Cart cart = cartRepository.save(new Cart());
        return cartMapper.toResponse(cart);
    }

    @Transactional(readOnly = true)
    public CartDto.Response getCart(Long cartId) {
        return cartMapper.toResponse(findCartOrThrow(cartId));
    }

    public void deleteCart(Long cartId) {
        cartRepository.delete(findCartOrThrow(cartId));
    }

    private Cart findCartOrThrow(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panier introuvable avec l'id : " + id));
    }

}
