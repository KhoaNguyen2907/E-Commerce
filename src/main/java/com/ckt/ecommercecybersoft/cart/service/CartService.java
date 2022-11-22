package com.ckt.ecommercecybersoft.cart.service;

import com.ckt.ecommercecybersoft.cart.constant.CartExceptionUtils;
import com.ckt.ecommercecybersoft.cart.dto.CartItemDTO;
import com.ckt.ecommercecybersoft.cart.dto.CartItemRequestDTO;
import com.ckt.ecommercecybersoft.cart.dto.CartItemResponseDTO;
import com.ckt.ecommercecybersoft.cart.model.CartItemEntity;
import com.ckt.ecommercecybersoft.cart.repository.CartRepository;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import com.ckt.ecommercecybersoft.product.util.ProductExceptionUtils;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CartService extends
        GenericService<CartItemEntity, CartItemResponseDTO, UUID> {
//    List<CartItemResponseDTO> getAllCartItem();

    List<CartItemDTO> getAllCartItemByUserId(UUID userId);

    CartItemResponseDTO setCartItem(CartItemRequestDTO cartItemRequestDTO);

    void deleteCartItem(UUID id);

}

@Service
@Transactional
class CartServiceImpl implements CartService {
    private final ProjectMapper mapper;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    CartServiceImpl(ProjectMapper mapper, CartRepository cartRepository, UserService userService, ProductService productService) {
        this.mapper = mapper;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

//    @Override
//    public List<CartItemResponseDTO> getAllCartItem() {
//        return cartRepository.findAll().stream()
//                .map(cartItemEntity -> {
//                    return new CartItemResponseDTO(
//                            mapper.map(cartItemEntity.getProduct(), ProductDTO.class),
//                            cartItemEntity.getQuantity(),
//                            cartItemEntity.getQuantity() *
//                                    cartItemEntity.getProduct().getPrice()
//                    );
//                })
//                .collect(Collectors.toList());
//    }

    @Override
    public List<CartItemDTO> getAllCartItemByUserId(UUID userId) {
        return cartRepository.findByUserId(userId).stream().map(cartItem -> mapper.map(cartItem, CartItemDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CartItemResponseDTO setCartItem(CartItemRequestDTO cartItemRequestDTO) {
//        UUID uuid = UUID.fromString("b2b5dd10-ac46-4445-aad0-5ad7b625480d");
//        List<CartItemEntity> users = cartRepository.findByUserIdAndProductId(
//                /*userService.getCurrentUser().getId(),*/
//                UUID.fromString("b2b5dd10-ac46-4445-aad0-5ad7b625480d"),
//                cartItemRequestDTO.getProductId()
//        );
//        if (cartItemRequestDTO.getQuantity() > 0) {
//            CartItemEntity cartItemTemp = CartItemEntity.builder()
//                    .user(userService.findById(UUID.fromString("64aca479-8d10-4ada-a8b2-da536cffa79e"))
//                            .orElse(null))
//                    .productEntity(productService.findById(cartItemRequestDTO.getProductId())
//                            .orElse(null))
//                    .quantity(cartItemRequestDTO.getQuantity())
//                    .build();
//
//            CartItemEntity cartItemEntity = cartRepository.findByUserIdAndProductId(
//                            /*userService.getCurrentUser().getId(),*/
//                            UUID.fromString("b2b5dd10-ac46-4445-aad0-5ad7b625480d"),
//                            cartItemRequestDTO.getProductId()
//                    ).stream().findFirst().map(cartItem -> {
//                        cartItem.setQuantity(cartItemTemp.getQuantity()
//                                + cartItem.getQuantity());
//                        return cartItem;
//                    }).orElse(cartItemTemp);
//            CartItemEntity savedCart = cartRepository.save(cartItemEntity);
//
//            return new CartItemResponseDTO(
//                    mapper.map(savedCart.getProductEntity(), ProductDTO.class),
//                    savedCart.getQuantity(),
//                    savedCart.getQuantity() * savedCart.getProductEntity().getPrice()
//            );
//
//        }
//        return null;

        CartItemDTO cartItemDTO = mapper.map(cartItemRequestDTO, CartItemDTO.class);
        if (cartItemDTO.getQuantity() == 0) {
            cartRepository.deleteById(cartItemDTO.getId());
            return null;
        }
        ProductEntity product = productService.findById(cartItemRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException(ProductExceptionUtils.PRODUCT_NOT_FOUND));
        ProductDTO productDto = mapper.map(product, ProductDTO.class);
        cartItemDTO.setProduct(productDto);
        cartItemDTO.setTotalPrice(productDto.getPrice() * cartItemDTO.getQuantity());
        logger.info("Cart item created: {}", cartItemDTO);

        UserDto userDto = userService.getCurrentUser().orElse(null);

        if (userDto != null) {
            CartItemEntity cartItem = cartRepository.findByUserIdAndProductId(
                    userDto.getId(),
                    cartItemDTO.getProduct().getId()
            );

            if (cartItem != null) {
                cartItem.setQuantity(cartItemDTO.getQuantity());
                cartItem.setTotalPrice(cartItemDTO.getTotalPrice());
            } else {
                cartItemDTO.setUser(userDto);
                cartItem = mapper.map(cartItemDTO, CartItemEntity.class);
            }

            CartItemEntity savedCart = cartRepository.save(cartItem);

            return mapper.map(savedCart, CartItemResponseDTO.class);
        }
        return mapper.map(cartItemDTO, CartItemResponseDTO.class);
    }

    @Override
    public void deleteCartItem(UUID id) {
       cartRepository.findById(id).orElseThrow(() -> new NotFoundException(CartExceptionUtils.CART_ITEM_NOT_FOUND));
       cartRepository.deleteById(id);
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
