package com.showback.controller;

import com.showback.dto.OrderDTO;
import com.showback.dto.OrderRequestDTO;
import com.showback.model.Order;
import com.showback.security.TokenProvider;
import com.showback.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequestDTO orderRequestDTO){

        Order order = orderService.createOrder(orderRequestDTO);

        return ResponseEntity.ok().body(order);
    }
}
