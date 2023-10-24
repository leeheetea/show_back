package com.showback.mapper;

import com.showback.dto.SeatDTO;
import com.showback.model.*;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SeatMapper {

    private final VenueRepository venueRepository;

    public SeatDTO toDTO(Seat seat) {
        if (seat == null) {
            return null;
        }

        SeatDTO dto = new SeatDTO();
        dto.setSeatId(seat.getSeatId());
        dto.setSeatRow(seat.getSeatRow());
        dto.setSeatColumn(seat.getSeatColumn());

        if (seat.getVenue() != null) {
            dto.setVenueId(seat.getVenue().getVenueId());
        }

        if (seat.getOrderDetails() != null) {
            dto.setOrderDetailIds(
                    seat.getOrderDetails().stream()
                            .map(OrderDetail::getOrderDetailId)
                            .collect(Collectors.toList()));
        }

        if (seat.getReservations() != null) {
            dto.setReservationIds(
                    seat.getReservations().stream()
                            .map(Reservation::getReservationId)
                            .collect(Collectors.toList()));
        }

        if (seat.getShowSeats() != null) {
            dto.setShowSeatIds(
                    seat.getShowSeats().stream()
                            .map(ShowSeat::getShowSeatId)
                            .collect(Collectors.toList()));
        }

        return dto;
    }

    public Seat toEntity(SeatDTO dto) {
        if (dto == null) {
            return null;
        }

        Seat seat = new Seat();
        seat.setSeatId(dto.getSeatId());
        seat.setSeatRow(dto.getSeatRow());
        seat.setSeatColumn(dto.getSeatColumn());


        if (dto.getVenueId() != null) {
            Venue venue = venueRepository.findById(dto.getVenueId()).orElseThrow(EntityNotFoundException::new);
            seat.setVenue(venue);
        }

        return seat;
    }
}

