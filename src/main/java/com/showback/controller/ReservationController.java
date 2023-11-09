package com.showback.controller;

import com.showback.dto.OrderRequestDTO;
import com.showback.dto.ReservationDTO;
import com.showback.model.Order;
import com.showback.model.Reservation;
import com.showback.service.OrderService;
import com.showback.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createReservation(
            @RequestBody OrderRequestDTO orderRequestDTO){

        Order order = orderService.createOrder(orderRequestDTO);

        return ResponseEntity.ok(order);
    }
}
