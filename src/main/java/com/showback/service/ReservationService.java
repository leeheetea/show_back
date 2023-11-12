package com.showback.service;

import com.showback.dto.ReservationResponseDTO;
import com.showback.dto.SeatDTO;
import com.showback.mapper.SeatMapper;
import com.showback.model.*;
import com.showback.repository.OrderRepository;
import com.showback.repository.ReservationRepository;
import com.showback.repository.ShowRepository;
import com.showback.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;
    private final OrderRepository orderRepository;

    private final UserAuthRepository userAuthRepository;
    private final SeatMapper seatMapper;

    public void createReservation(Order order, Long showId){

        int price = 0;

        Show show = showRepository.findById(showId)
                .orElseThrow(EntityNotFoundException::new);
        List<OrderDetail> orderDetails = order.getOrderDetails();


        for (OrderDetail orderDetail : orderDetails){
            price += orderDetail.getFinalPrice();
        }

        Reservation reservation = Reservation.builder()
                .date(order.getOrderDate())
                .state(order.getOrderState())
                .showName(show.getTitle())
                .showVenue(show.getVenue().getVenueName())
                .price(price)
                .ticketAmount(order.getTicketAmount())
                .order(order)
                .show(show)
                .build();

        order.setReservation(reservation);
        reservationRepository.save(reservation);
    }

    public ReservationResponseDTO findReservation(Long userId, Long orderId) {

        UserAuth userAuth = null;
        try {
            userAuth = userAuthRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        List<Seat> seats = order.getOrderDetails().stream()
                .map(orderDetail -> orderDetail.getShowSeat().getSeat())
                .collect(Collectors.toList());

        List<SeatDTO> seatDTOS = seats.stream().map(seatMapper::toDTO)
                .collect(Collectors.toList());

        Reservation reservation = reservationRepository.findByOrder(order);

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        reservationResponseDTO.setReservationId(reservation.getReservationId());
        reservationResponseDTO.setUserName(userAuth.getAuthName());
        reservationResponseDTO.setVenueName(reservation.getShowVenue());
        reservationResponseDTO.setShowImgUrl(reservation.getShow().getThumbnailUrl());
        reservationResponseDTO.setSeat(seatDTOS);
        reservationResponseDTO.setOrderState(order.getOrderState());
        reservationResponseDTO.setShowDate(order.getOrderDate());

        return reservationResponseDTO;
    }
}
