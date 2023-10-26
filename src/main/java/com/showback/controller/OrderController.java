package com.showback.controller;

import com.showback.dto.OrderDTO;
import com.showback.model.Order;
import com.showback.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            OrderDTO orderDTO,

            @Param("showID") Long showId){

        Order order = orderService.createOrder(orderDTO, 1L, showId);
        return null;
    }
}
