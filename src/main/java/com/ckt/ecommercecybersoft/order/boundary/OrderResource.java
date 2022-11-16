package com.ckt.ecommercecybersoft.order.boundary;

import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.order.constant.OrderUrl;
import com.ckt.ecommercecybersoft.order.dto.RequestOrderDTO;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderDTO;
import com.ckt.ecommercecybersoft.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = OrderUrl.URL_ORDER)
@RestController
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<ResponseOrderDTO> getAllOrder() {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody RequestOrderDTO requestOrderDTO) {
        return ResponseUtils.get(
                orderService.createOrder(requestOrderDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateOrder(
            @RequestBody RequestOrderDTO requestOrderDTO,
            @PathVariable UUID id){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOrder() {
        return null;
    }

}
