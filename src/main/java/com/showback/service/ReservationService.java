package com.showback.service;

import com.showback.model.Order;
import com.showback.model.OrderDetail;
import com.showback.model.Reservation;
import com.showback.model.Show;
import com.showback.repository.ReservationRepository;
import com.showback.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;

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
}
