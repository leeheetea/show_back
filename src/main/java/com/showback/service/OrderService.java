package com.showback.service;

import com.showback.dto.OrderDTO;
import com.showback.dto.OrderDetailDTO;
import com.showback.dto.OrderRequestDTO;
import com.showback.mapper.OrderDetailMapper;
import com.showback.mapper.ReservationMapper;
import com.showback.model.*;
import com.showback.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final UserAuthRepository userAuthRepository;
    private final ShowRepository showRepository;
    private final ReservationRepository reservationRepository;

    private final OrderDetailMapper orderDetailMapper;
    private final ReservationMapper reservationMapper;


    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO, Long userId, Long showId) {


        return null;
    }
}
