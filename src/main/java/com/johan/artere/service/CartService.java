package com.johan.artere.service;

import com.johan.artere.dto.CartDto;
import com.johan.artere.exception.ResourceNotFoundException;
import com.johan.artere.mapper.CartMapper;
import com.johan.artere.model.Cart;
import com.johan.artere.model.CartItem;
import com.johan.artere.model.Product;
import com.johan.artere.repository.CartItemRepository;
import com.johan.artere.repository.CartRepository;
import com.johan.artere.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
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

    public CartDto.Response addItem(Long cartId, CartDto.ItemRequest request) {
        Cart cart = findCartOrThrow(cartId);
        Product product = findProductOrThrow(request.getProductId());

        cartItemRepository.findByCartIdAndProductId(cartId, product.getId())
                .ifPresentOrElse(
                        existing -> existing.setQuantity(existing.getQuantity() + request.getQuantity()),
                        () -> {
                            CartItem item = CartItem.builder()
                                    .cart(cart)
                                    .product(product)
                                    .quantity(request.getQuantity())
                                    .build();
                            cart.getItems().add(cartItemRepository.save(item));
                        }
                );

        return cartMapper.toResponse(cart);
    }

    public CartDto.Response updateItem(Long cartId, Long itemId, CartDto.ItemRequest request) {
        findCartOrThrow(cartId);
        CartItem item = findItemOrThrow(itemId);
        item.setQuantity(request.getQuantity());
        return cartMapper.toResponse(findCartOrThrow(cartId));
    }

    public CartDto.Response removeItem(Long cartId, Long itemId) {
        Cart cart = findCartOrThrow(cartId);
        CartItem item = findItemOrThrow(itemId);
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        return cartMapper.toResponse(cart);
    }

    public CartDto.Response clearCart(Long cartId) {
        Cart cart = findCartOrThrow(cartId);
        cart.getItems().clear();
        return cartMapper.toResponse(cart);
    }

    public void deleteCart(Long cartId) {
        cartRepository.delete(findCartOrThrow(cartId));
    }

    private Cart findCartOrThrow(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panier introuvable avec l'id : " + id));
    }

    private CartItem findItemOrThrow(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article introuvable avec l'id : " + id));
    }

    private Product findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.product(id));
    }
}
