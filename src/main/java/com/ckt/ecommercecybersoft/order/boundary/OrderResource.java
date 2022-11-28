package com.ckt.ecommercecybersoft.order.boundary;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.order.constant.OrderUrl;
import com.ckt.ecommercecybersoft.order.dto.OrderDTO;
import com.ckt.ecommercecybersoft.order.dto.RequestOrderDTO;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderDTO;
import com.ckt.ecommercecybersoft.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RequestMapping(value = OrderUrl.URL_ORDER)
@RestController
public class OrderResource {
    @Autowired
    private OrderService orderService;
    @Autowired
    ProjectMapper mapper;


    @GetMapping
    public ResponseEntity<ResponseDTO> getAllOrder() {
        List<ResponseOrderDTO> responseOrderDTOs = orderService.findAllDto(OrderDTO.class).stream()
                .map(orderDTO -> mapper.map(orderDTO, ResponseOrderDTO.class))
                .collect(Collectors.toList());
        return ResponseUtils.get(responseOrderDTOs,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createOrder(
            @RequestBody RequestOrderDTO requestOrderDTO) {
        return ResponseUtils.get(orderService.createOrder(requestOrderDTO), HttpStatus.CREATED);
    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<ResponseDTO> updateOrder(
//            @RequestBody RequestOrderDTO requestOrderDTO,
//            @PathVariable UUID id){
//        return null;
//    }

//    @DeleteMapping
//    public ResponseEntity<ResponseDTO> deleteOrder() {
//        return null;
//    }

}
