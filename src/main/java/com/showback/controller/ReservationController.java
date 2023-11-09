package com.showback.controller;

import com.showback.dto.OrderRequestDTO;
import com.showback.dto.ReservationDTO;
import com.showback.model.Order;
import com.showback.model.Reservation;
import com.showback.service.OrderService;
import com.showback.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createReservation(
            @RequestBody OrderRequestDTO orderRequestDTO){

        try {
            Order order = orderService.createOrder(orderRequestDTO);
            reservationService.createReservation(order, orderRequestDTO.getShowId());
            return ResponseEntity.ok().body("예매가 성공적으로 처리 됐습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자/좌석을 찾을 수 없습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("좌석이 이미 예약되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 처리 중 에러가 발생했습니다.");
        }
    }
}
