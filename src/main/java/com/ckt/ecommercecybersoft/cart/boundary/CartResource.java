package com.ckt.ecommercecybersoft.cart.boundary;

import com.ckt.ecommercecybersoft.cart.constant.CartUrl;
import com.ckt.ecommercecybersoft.cart.dto.CartItemRequestDTO;
import com.ckt.ecommercecybersoft.cart.dto.CartItemResponseDTO;
import com.ckt.ecommercecybersoft.cart.service.CartService;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(CartUrl.URL_CART)
@RestController
public class CartResource {
    private final CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemResponseDTO> getCartItem() {
        return cartService.getAllCartItem();
    }

    @PostMapping
    public ResponseEntity<?> createCartItem(
            @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseUtils.get(
                cartService.createCartItem(cartItemRequestDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCartItem(
            @RequestBody CartItemRequestDTO cartItemRequestDTO,
            @PathVariable UUID cartId
    ) {
        return ResponseUtils.get(
                cartService.saveCartItem(cartId, cartItemRequestDTO),
                HttpStatus.CREATED
        );
    }
}
