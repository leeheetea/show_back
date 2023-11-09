package com.showback.service;

import com.showback.model.Order;
import com.showback.dto.OrderDetailDTO;
import com.showback.dto.OrderRequestDTO;
import com.showback.model.UserAuth;
import com.showback.repository.OrderRepository;
import com.showback.repository.UserAuthRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import com.showback.model.OrderDetail;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserAuthRepository userAuthRepository;

    @Mock
    private OrderDetailService orderDetailService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        Long userId = 1L;
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(); // populate DTO
        UserAuth mockUserAuth = new UserAuth(); // assuming existence of a no-arg constructor
        when(userAuthRepository.findById(userId)).thenReturn(Optional.of(mockUserAuth));
        when(orderDetailService.createOrderDetail(any(OrderDetailDTO.class))).thenReturn(new OrderDetail());

        // Act
        Order result;
        result = orderService.createOrder(orderRequestDTO);

        // Assert
        verify(userAuthRepository).findById(userId);
        verify(orderDetailService, times(orderRequestDTO.getSelectedSeats().size())).createOrderDetail(any(OrderDetailDTO.class));
        verify(orderRepository).save(any(Order.class));
    }
}
