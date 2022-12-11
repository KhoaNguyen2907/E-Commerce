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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
        List<ResponseOrderDTO> responseOrderDTOs = orderService.findAllDto(Sort.by("createdAt").descending(), OrderDTO.class).stream()
                .map(orderDTO -> mapper.map(orderDTO, ResponseOrderDTO.class))
                .collect(Collectors.toList());
        return ResponseUtils.get(responseOrderDTOs,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createOrder(
            @RequestBody RequestOrderDTO requestOrderDTO) {
        ResponseOrderDTO responseOrderDTO = orderService.createOrder(requestOrderDTO);
        if (responseOrderDTO.getStatus().equals("FAILED")) {
            return ResponseUtils.get(responseOrderDTO, HttpStatus.BAD_REQUEST);
        }
        return ResponseUtils.get(responseOrderDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{status}")
    public ResponseEntity<ResponseDTO> updateOrder(
            @PathVariable UUID id, @PathVariable String status) {
        ResponseOrderDTO responseOrderDTO = orderService.updateOrderStatus(id, status);
        if (responseOrderDTO.getStatus().equals("FAILED")) {
            return ResponseUtils.get(responseOrderDTO, HttpStatus.BAD_REQUEST);
        }
        return ResponseUtils.get(responseOrderDTO, HttpStatus.OK);
    }

//    @DeleteMapping
//    public ResponseEntity<ResponseDTO> deleteOrder() {
//        return null;
//    }

}
