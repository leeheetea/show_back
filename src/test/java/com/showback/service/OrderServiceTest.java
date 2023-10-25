package com.showback.service;

import com.showback.dto.OrderDTO;
import com.showback.mapper.OrderDetailMapper;
import com.showback.mapper.ReservationMapper;
import com.showback.model.Order;
import com.showback.model.Show;
import com.showback.model.UserAuth;
import com.showback.repository.OrderDetailRepository;
import com.showback.repository.OrderRepository;
import com.showback.repository.ShowRepository;
import com.showback.repository.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private UserAuthRepository userAuthRepository;

    @Mock
    private OrderDetailMapper orderDetailMapper;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private ShowRepository showRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrder() {
        // Given
        OrderDTO orderDTO = new OrderDTO();  // Populate this DTO with the required fields for the test
        Long userId = 1L;
        Long showId = 1L;
        when(userAuthRepository.findByUserId(userId)).thenReturn(new UserAuth());
        when(showRepository.findById(showId)).thenReturn(Optional.of(new Show()));
        when(orderDetailRepository.countByOrderId(any())).thenReturn(1);

        // When
        Order resultOrder = orderService.createOrder(orderDTO, userId, showId);

        // Then
        verify(userAuthRepository, times(1)).findByUserId(userId);
        verify(showRepository, times(1)).findById(showId);

    }
}