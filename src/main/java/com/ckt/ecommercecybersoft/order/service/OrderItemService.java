//package com.ckt.ecommercecybersoft.order.service;
//import com.ckt.ecommercecybersoft.common.service.GenericService;
//import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
//import com.ckt.ecommercecybersoft.order.dto.OrderItemDto;
//import com.ckt.ecommercecybersoft.order.model.OrderItem;
//import com.ckt.ecommercecybersoft.order.repository.OrderItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//public interface OrderItemService extends GenericService<OrderItem, OrderItemDto, UUID> {
//}
//
//@Service
//@Transactional
//class OrderItemServiceImpl implements OrderItemService {
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//    @Autowired
//    private ProjectMapper mapper;
//
//    @Override
//    public JpaRepository<OrderItem, UUID> getRepository() {
//        return this.orderItemRepository;
//    }
//
//    @Override
//    public ProjectMapper getMapper() {
//        return this.mapper;
//    }
//}
