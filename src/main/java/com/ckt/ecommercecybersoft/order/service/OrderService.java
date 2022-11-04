package com.ckt.ecommercecybersoft.order.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.order.dto.RequestOrderDTO;
import com.ckt.ecommercecybersoft.order.dto.RequestOrderItemDTO;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderDTO;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderItemDTO;
import com.ckt.ecommercecybersoft.order.model.OrderEntity;
import com.ckt.ecommercecybersoft.order.repository.OrderRepository;
import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.address.model.AddressEntity;
import com.ckt.ecommercecybersoft.address.service.AddressService;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface OrderService extends
        GenericService<OrderEntity, RequestOrderDTO, UUID> {
    public ResponseOrderDTO createOrder(RequestOrderDTO requestOrderDTO);
}

@Service
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProjectMapper mapper;

    private final AddressService addressService;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProjectMapper mapper,
                            AddressService addressService,
                            ProductService productService) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.addressService = addressService;
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
        AddressDTO addressDTO = requestOrderDTO.getAddress();
        UUID addressId = addressDTO.getId();
        List<RequestOrderItemDTO> requestOrderItemDTOs =
                requestOrderDTO.getRequestOrderItemDTOs();
//        if(addressId == null) {
//            AddressEntity addressEntity =
//                    mapper.map(addressDTO, AddressEntity.class);
//            addressService.getRepository().save(addressEntity );
//        }
//        AddressEntity addressEntity = addressId == null ?
        AddressEntity addressEntity =
                addressService.findById(addressId)
                        .orElse(mapper.map(addressDTO, AddressEntity.class));
        addressService.getRepository().save(addressEntity);
        List<UUID> productIds = requestOrderItemDTOs.stream()
                .map(requestOrderItemDTO -> requestOrderItemDTO.getProductId())
                .collect(Collectors.toList());
        List<ProductEntity> productEntities = productService.findAllByIds(productIds);
//        TypeMap<RequestOrderDTO, OrderEntity> propertyMapper =
//                mapper.createTypeMap(RequestOrderDTO.class, OrderEntity.class);
//        propertyMapper.addMappings(mapper->mapper.map(
//                orderDTO -> null, OrderEntity::setOrderItems
//        ));
        OrderEntity orderEntity = mapper.map(requestOrderDTO, OrderEntity.class);

        IntStream.range(0, requestOrderItemDTOs.size()).forEach(i -> {
            if(productEntities.get(i).getId()
                    .equals(requestOrderItemDTOs.get(i).getProductId())) {
                orderEntity.addProduct(productEntities.get(i),
                        requestOrderItemDTOs.get(i).getQuantity());
            }
        });
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        List<ResponseOrderItemDTO> ResponseOrderItemDTOs =
                savedOrderEntity.getOrderItems().stream()
                        .map(orderItem -> {
                            return new ResponseOrderItemDTO(
                                    mapper.map(
                                            orderItem.getProductEntity(),
                                            ProductDTO.class),
                                    orderItem.getQuantity()
                            );
                        }).collect(Collectors.toList());
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO(
                mapper.map(savedOrderEntity.getAddress(), AddressDTO.class),
                savedOrderEntity.getComment(),
                savedOrderEntity.getStatus(),
                savedOrderEntity.getTotalCost(),
                ResponseOrderItemDTOs

        );
        return responseOrderDTO;
    }

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

    }
}
