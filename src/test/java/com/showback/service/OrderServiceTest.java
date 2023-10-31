package com.showback.service;

import com.showback.dto.OrderDTO;
import com.showback.mapper.OrderDetailMapper;
import com.showback.mapper.ReservationMapper;
import com.showback.model.*;
import com.showback.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

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

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderTest() {
        // Given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setOrderDetails(Collections.emptyList());

        UserAuth userAuth = new UserAuth();
        Show show = new Show();
        show.setTitle("Test Show");
        Venue venue = new Venue();
        venue.setVenueName("Test Venue");
        show.setVenue(venue);

        when(orderDetailRepository.countByOrderId(anyLong())).thenReturn(1);
        when(userAuthRepository.findByUserId(anyLong())).thenReturn(userAuth);
        when(showRepository.findById(anyLong())).thenReturn(Optional.of(show));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());


        // When
        Order result = orderService.createOrder(orderDTO, 1L, 1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTicketAmount());
    }
}
