package com.showback.service;

import com.showback.dto.SeatDTO;
import com.showback.model.Seat;
import com.showback.model.Venue;
import com.showback.repository.SeatRepository;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final VenueRepository venueRepository;

    @Transactional
    public Seat createSeat(SeatDTO seatDTO) {
        Seat seat = new Seat();

        seat.setSeatRow(seatDTO.getSeatRow());
        seat.setSeatColumn(seatDTO.getSeatColumn());

        if (seatDTO.getVenueId() != null) {
            Venue venue = venueRepository.findById(seatDTO.getVenueId())
                    .orElseThrow(() -> new EntityNotFoundException("Venue not found with ID: " + seatDTO.getVenueId()));
            seat.setVenue(venue);
        }
        return seatRepository.save(seat);
    }
}
