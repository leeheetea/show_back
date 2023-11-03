package com.showback.service;

import com.showback.dto.OrderDTO;
import com.showback.dto.OrderDetailDTO;
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
    public Order createOrder(OrderDTO orderDTO, Long userId, Long showId){
        Order order = new Order();

        order.setTicketAmount(orderDTO.getTicketAmount());
        order.setOrderState(orderDTO.getOrderState());
        order.setOrderDate(new Date());

        UserAuth userAuth = userAuthRepository.findByUserId(userId);
        if(userAuth != null){
            order.setUserAuth(userAuth);
        }

        if (orderDTO.getOrderDetails() != null) {
            List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                    .map(orderDetailMapper::toEntity)
                    .collect(Collectors.toList());
            order.setOrderDetails(orderDetails);
        }

        //예약생성
       if (orderDTO.getReservation() != null){
           Reservation reservation = reservationMapper.toEntity(orderDTO.getReservation());
           order.setReservation(reservation);
       } else {
           Show show = showRepository.findById(showId)
                   .orElseThrow(() -> new EntityNotFoundException("Show with ID " + showId + " not found."));

           List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                   .map(orderDetailMapper::toEntity)
                   .collect(Collectors.toList());

           int reservationPrice = 0;
           for(OrderDetail orderDetail : orderDetails ){
               reservationPrice += orderDetail.getFinalPrice();
           }

           Reservation reservation = Reservation.builder()
                   .reservationDate(new Date())
                   .reservationState("입금 대기중")
                   .reservationShowName(show.getTitle())
                   .reservationShowVenue(show.getVenue().getVenueName())
                   .reservationPrice(reservationPrice)
                   .reservationTicketAmount(orderDTO.getTicketAmount())
                   .order(order)
                   .show(show)
                   .build();

           reservationRepository.save(reservation);
       }
       return order;
    }
}
