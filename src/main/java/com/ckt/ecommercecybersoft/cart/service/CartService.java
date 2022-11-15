package com.ckt.ecommercecybersoft.cart.service;

import com.ckt.ecommercecybersoft.cart.dto.CartItemRequestDTO;
import com.ckt.ecommercecybersoft.cart.model.CartItemEntity;
import com.ckt.ecommercecybersoft.cart.dto.CartItemResponseDTO;
import com.ckt.ecommercecybersoft.cart.repository.CartRepository;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CartService extends
        GenericService<CartItemEntity, CartItemResponseDTO, UUID> {
    public List<CartItemResponseDTO> getAllCartItem();

    public CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO);

    public CartItemResponseDTO saveCartItem(UUID cartId,
                                            CartItemRequestDTO cartItemRequestDTO);
}

@Service
class CartServiceImpl implements CartService {
    private final ProjectMapper mapper;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    CartServiceImpl(ProjectMapper mapper, CartRepository cartRepository, UserService userService, ProductService productService) {
        this.mapper = mapper;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public List<CartItemResponseDTO> getAllCartItem() {
        return cartRepository.findAll().stream()
                .map(cartItemEntity -> {
                    return new CartItemResponseDTO(
                            mapper.map(cartItemEntity.getProductEntity(), ProductDTO.class),
                            cartItemEntity.getQuantity(),
                            cartItemEntity.getQuantity() *
                                    cartItemEntity.getProductEntity().getPrice()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public CartItemResponseDTO createCartItem(
            CartItemRequestDTO cartItemRequestDTO) {
        UUID uuid = UUID.fromString("b2b5dd10-ac46-4445-aad0-5ad7b625480d");
        List<CartItemEntity> users = cartRepository.findByUserIdAndProductId(
                /*userService.getCurrentUser().getId(),*/
                UUID.fromString("b2b5dd10-ac46-4445-aad0-5ad7b625480d"),
                cartItemRequestDTO.getProductId()
        );
        if (cartItemRequestDTO.getQuantity() > 0) {
            CartItemEntity cartItemTemp = CartItemEntity.builder()
                    .user(userService.findById(UUID.fromString("64aca479-8d10-4ada-a8b2-da536cffa79e"))
                            .orElse(null))
                    .productEntity(productService.findById(cartItemRequestDTO.getProductId())
                            .orElse(null))
                    .quantity(cartItemRequestDTO.getQuantity())
                    .build();

            CartItemEntity cartItemEntity = cartRepository.findByUserIdAndProductId(
                            /*userService.getCurrentUser().getId(),*/
                            UUID.fromString("b2b5dd10-ac46-4445-aad0-5ad7b625480d"),
                            cartItemRequestDTO.getProductId()
                    ).stream().findFirst().map(cartItem -> {
                        cartItem.setQuantity(cartItemTemp.getQuantity()
                                + cartItem.getQuantity());
                        return cartItem;
                    }).orElse(cartItemTemp);
            CartItemEntity savedCart = cartRepository.save(cartItemEntity);

            return new CartItemResponseDTO(
                    mapper.map(savedCart.getProductEntity(), ProductDTO.class),
                    savedCart.getQuantity(),
                    savedCart.getQuantity() * savedCart.getProductEntity().getPrice()
            );
        }
        return null;
    }

    @Override
    public CartItemResponseDTO saveCartItem(UUID cartId, CartItemRequestDTO cartItemRequestDTO) {
        CartItemEntity cartItemEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));
        return mapper.map(
                cartRepository.save(cartItemEntity),
                CartItemResponseDTO.class
        );
    }

    @Override
    public JpaRepository<CartItemEntity, UUID> getRepository() {
        return cartRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }
}
