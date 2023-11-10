package com.showback.controller;

import com.showback.dto.OrderDTO;
import com.showback.dto.OrderRequestDTO;
import com.showback.model.Order;
import com.showback.security.TokenProvider;
import com.showback.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(("/order"))
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            Order order = orderService.createOrder(orderRequestDTO);
            return ResponseEntity.ok().body(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자/좌석을 찾을 수 없습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("좌석이 이미 예약되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 처리 중 에러가 발생했습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrder(
            HttpServletRequest request
    ){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

        List<OrderDTO> orderDTOs = orderService.findByOrder(userId);

        return ResponseEntity.ok().body(orderDTOs);
    }
}
