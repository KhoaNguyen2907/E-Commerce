package com.ckt.ecommercecybersoft.order.service;

import com.ckt.ecommercecybersoft.cart.dto.CartItemDTO;
import com.ckt.ecommercecybersoft.cart.service.CartService;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.order.dto.OrderDTO;
import com.ckt.ecommercecybersoft.order.dto.OrderItemDto;
import com.ckt.ecommercecybersoft.order.dto.RequestOrderDTO;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderDTO;
import com.ckt.ecommercecybersoft.order.model.OrderEntity;
import com.ckt.ecommercecybersoft.order.model.OrderItem;
import com.ckt.ecommercecybersoft.order.repository.OrderItemRepository;
import com.ckt.ecommercecybersoft.order.repository.OrderRepository;
import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface OrderService extends
        GenericService<OrderEntity, OrderDTO, UUID> {
    public ResponseOrderDTO createOrder(RequestOrderDTO requestOrderDTO);
    public ResponseOrderDTO updateOrderStatus(UUID id, String status);

}

@Service
@Transactional
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProjectMapper mapper;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProjectMapper mapper,
                            UserService userService,
                            CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }


    @Override
    public JpaRepository<OrderEntity, UUID> getRepository() {
        return orderRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }

    public ResponseOrderDTO createOrder(RequestOrderDTO requestOrderDTO) {
        OrderDTO orderDto = mapper.map(requestOrderDTO, OrderDTO.class);

        UserDto userDto = userService.getCurrentUser().orElse(null);
        //Set user if user is logged in
        orderDto.setUser(userDto);

        //set status
        orderDto.setStatus(OrderEntity.Status.PENDING);

        OrderEntity order = mapper.map(orderDto, OrderEntity.class);
        order.getOrderItems().clear();
        List<OrderItemDto> orderItemDtos = requestOrderDTO.getOrderItems().stream().map(oid -> {
            OrderItemDto orderItemDto = mapper.map(oid, OrderItemDto.class);
            ProductEntity product = productService.findById(oid.getProductId()).orElseThrow(() -> new NotFoundException("099 Product not found"));
            orderItemDto.setProduct(product);

            if (userDto != null) {
                List<CartItemDTO> cart = cartService.getAllCartItemByUserId(userDto.getId());
                cart.forEach(cartItem -> {
                    if (cartItem.getProduct().getId().equals(oid.getProductId())) {
                        cartService.deleteCartItem(cartItem.getId());
                    }
                });
            }
            order.addOrderItem(mapper.map(orderItemDto, OrderItem.class));
            return orderItemDto;
        }).collect(Collectors.toList());

        //set total price
        long total = orderItemDtos.stream().mapToLong(item -> item.getQuantity() * item.getProduct().getPrice()).sum();
        order.setTotalCost(total);

        OrderDTO savedOrder = mapper.map(orderRepository.save(order), OrderDTO.class);

        return mapper.map(savedOrder, ResponseOrderDTO.class);

    }

    @Override
    public ResponseOrderDTO updateOrderStatus(UUID id, String status) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("099 Order not found"));
        order.setStatus(OrderEntity.Status.valueOf(status));
        OrderDTO savedOrder = mapper.map(orderRepository.save(order), OrderDTO.class);
        return mapper.map(savedOrder, ResponseOrderDTO.class);
    }

//        AddressDTO addressDTO = requestOrderDTO.getAddress();
//        UUID addressId = addressDTO.getId();
//        List<RequestOrderItemDTO> requestOrderItemDTOs =
//                requestOrderDTO.getRequestOrderItemDTOs();
////        if(addressId == null) {
////            AddressEntity addressEntity =
////                    mapper.map(addressDTO, AddressEntity.class);
////            addressService.getRepository().save(addressEntity );
////        }
////        AddressEntity addressEntity = addressId == null ?
//        AddressEntity addressEntity =
//                addressService.findById(addressId)
//                        .orElse(mapper.map(addressDTO, AddressEntity.class));
//        addressService.getRepository().save(addressEntity);
//        List<UUID> productIds = requestOrderItemDTOs.stream()
//                .map(requestOrderItemDTO -> requestOrderItemDTO.getProductId())
//                .collect(Collectors.toList());
//        List<ProductEntity> productEntities = productService.findAllByIds(productIds);
////        TypeMap<RequestOrderDTO, OrderEntity> propertyMapper =
////                mapper.createTypeMap(RequestOrderDTO.class, OrderEntity.class);
////        propertyMapper.addMappings(mapper->mapper.map(
////                orderDTO -> null, OrderEntity::setOrderItems
////        ));
//        OrderEntity orderEntity = mapper.map(requestOrderDTO, OrderEntity.class);
//
//        IntStream.range(0, requestOrderItemDTOs.size()).forEach(i -> {
//            if(productEntities.get(i).getId()
//                    .equals(requestOrderItemDTOs.get(i).getProductId())) {
//                orderEntity.addProduct(productEntities.get(i),
//                        requestOrderItemDTOs.get(i).getQuantity());
//            }
//        });
//        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
//        List<ResponseOrderItemDTO> responseOrderItemDTOs =
//                savedOrderEntity.getOrderItems().stream()
//                        .map(orderItem -> {
//                            return new ResponseOrderItemDTO(
//                                    mapper.map(
//                                            orderItem.getProductEntity(),
//                                            ProductDTO.class),
//                                    orderItem.getQuantity()
//                            );
//                        }).collect(Collectors.toList());
//        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO(
//                mapper.map(savedOrderEntity.getAddress(), AddressDTO.class),
//                savedOrderEntity.getComment(),
//                savedOrderEntity.getStatus(),
//                savedOrderEntity.getTotalCost(),
//                responseOrderItemDTOs,
//                mapper.map(savedOrderEntity.getUser(), UserDto.class)
//        );
//        return responseOrderDTO;
//    }


//    private List<ResponseOrderItemDTO> getResponseOrderItemDTOs(
//            List<ResponseOrderItemDTO> requestOrderItemDTOs,
//            List<ProductEntity> productEntities
//    ) {
//        List<ResponseOrderItemDTO> responseOrderItemDTOs =
//                IntStream.range(0, requestOrderItemDTOs.size())
//                        .mapToObj(i -> {
//                            if(requestOrderItemDTOs.get(i)
//                                    .equals(productEntities.get(i).getId())) {
//                                return new ResponseOrderItemDTO(
//                                        productEntities.get(i),
//                                        requestOrderItemDTOs.get(i).getQuantity()
//                                );
//                            } else {
//                                return null;
//                            }
//                        }).collect(Collectors.toList());
//        return responseOrderItemDTOs;
//    }

    public void deleteOrderById(UUID id) {
        orderRepository.findById(id).orElseThrow(() -> new NotFoundException("099 Order not found"));
        orderRepository.deleteById(id);
    }
}
