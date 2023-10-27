package com.showback.controller;

import com.showback.dto.OrderDTO;
import com.showback.model.Order;
import com.showback.security.TokenProvider;
import com.showback.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderTest() {
        // Given
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        when(request.getHeader("Authorization")).thenReturn("Bearer someToken");
        when(tokenProvider.validateAndGetUserId("someToken")).thenReturn("1");
        when(orderService.createOrder(any(), anyLong(), anyLong())).thenReturn(order);

        // When
        ResponseEntity<?> result = orderController.createOrder(orderDTO, request, 1L);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(order, result.getBody());
    }
}
