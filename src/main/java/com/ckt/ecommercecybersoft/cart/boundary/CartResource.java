package com.ckt.ecommercecybersoft.cart.boundary;

import com.ckt.ecommercecybersoft.cart.constant.CartUrl;
import com.ckt.ecommercecybersoft.cart.dto.CartItemRequestDTO;
import com.ckt.ecommercecybersoft.cart.dto.CartItemResponseDTO;
import com.ckt.ecommercecybersoft.cart.service.CartService;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(value = "https://black-adam-web.herokuapp.com/", allowCredentials = "true")
@RequestMapping(CartUrl.URL_CART)
@RestController
public class CartResource {
    private final CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add item to user cart, if item already exists, update quantity. If no user is logged in, just return the item for frond end to handle.
     * @param cartItemRequestDTO item to add to user cart, include product and quantity
     * @return item added with user or just the item if no user is logged in
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> addCartItem(
            @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        CartItemResponseDTO cartItemResponseDTO = cartService.createCartItem(cartItemRequestDTO);
        return ResponseUtils.get(cartItemResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = CartUrl.BY_ID)
    public ResponseEntity<ResponseDTO> deleteCartItem(
            @PathVariable UUID id) {
        cartService.deleteCartItem(id);
        return ResponseUtils.get(null, HttpStatus.NO_CONTENT);
    }
}
