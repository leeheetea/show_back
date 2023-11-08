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
    }
}
