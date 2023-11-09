package com.showback.mapper;

import com.showback.dto.ReservationDTO;
import com.showback.model.Order;
import com.showback.model.Reservation;
import com.showback.model.Seat;
import com.showback.model.Show;
import com.showback.repository.OrderRepository;
import com.showback.repository.SeatRepository;
import com.showback.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    // DTO -> Entity
    public Reservation toEntity(ReservationDTO reservationDTO) {
        Order order = orderRepository.findById(reservationDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + reservationDTO.getOrderId() + " not found."));
        Show show = showRepository.findById(reservationDTO.getShowId())
                .orElseThrow(() -> new EntityNotFoundException("Show with ID " + reservationDTO.getShowId() + " not found."));

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationDTO.getReservationId());
        reservation.setDate(reservationDTO.getReservationDate());
        reservation.setState(reservationDTO.getReservationState());
        reservation.setShowName(reservationDTO.getReservationShowName());
        reservation.setShowVenue(reservationDTO.getReservationShowVenue());
        reservation.setPrice(reservationDTO.getReservationPrice());
        reservation.setTicketAmount(reservationDTO.getReservationTicketAmount());
        reservation.setOrder(order);
        reservation.setShow(show);

        return reservation;
    }

    // Entity -> DTO
    public ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(reservation.getReservationId());
        reservationDTO.setReservationDate(reservation.getDate());
        reservationDTO.setReservationState(reservation.getState());
        reservationDTO.setReservationShowName(reservation.getShowName());
        reservationDTO.setReservationShowVenue(reservation.getShowVenue());
        reservationDTO.setReservationPrice(reservation.getPrice());
        reservationDTO.setReservationTicketAmount(reservation.getTicketAmount());
        reservationDTO.setOrderId(reservation.getOrder().getOrderId());
        reservationDTO.setShowId(reservation.getShow().getShowId());

        return reservationDTO;
    }
}
