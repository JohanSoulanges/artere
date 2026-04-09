package com.johan.artere;

import com.johan.artere.dto.CartDto;
import com.johan.artere.mapper.CartMapper;
import com.johan.artere.model.Cart;
import com.johan.artere.model.CartItem;
import com.johan.artere.model.Product;
import com.johan.artere.repository.CartRepository;
import com.johan.artere.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private Product product;
    private CartItem cartItem;
    private CartDto.Response cartResponse;
    private CartDto.ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());

        product = new Product();
        product.setId(10L);

        cartItem = CartItem.builder()
                .id(100L)
                .cart(cart)
                .product(product)
                .quantity(2)
                .build();

        cartResponse = new CartDto.Response();

        itemRequest = new CartDto.ItemRequest();
        itemRequest.setProductId(10L);
        itemRequest.setQuantity(3);
    }

    @Test
    @DisplayName("createCart - crée et retourne un nouveau panier")
    void createCart_shouldSaveAndReturnCartResponse() {
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toResponse(cart)).thenReturn(cartResponse);

        CartDto.Response result = cartService.createCart();

        assertThat(result).isEqualTo(cartResponse);
        verify(cartRepository).save(any(Cart.class));
        verify(cartMapper).toResponse(cart);
    }
}
