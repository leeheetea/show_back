package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.VenueDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.mapper.VenueMapper;
import com.showback.model.Seat;
import com.showback.model.Venue;
import com.showback.repository.SeatRepository;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;
    private final SeatRepository seatRepository;

    @Transactional
    public VenueDTO findById(Long venueId) throws JsonProcessingException {
        Venue foundVenue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ShowNotFoundException(venueId));
        return venueMapper.toDTO(foundVenue);
    }

    @Transactional
    public Venue createVenueWithSeats(VenueDTO venueDTO) {
        Venue venue = venueMapper.toEntity(venueDTO);
        venue = venueRepository.save(venue);

        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= venue.getSeatMaxRow(); row++) {
            for (int col = 1; col <= venue.getSeatMaxColumn(); col++) {
                Seat seat = new Seat();
                seat.setSeatRow(row);
                seat.setSeatColumn(col);
                seat.setVenue(venue);
                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
        return venue;
    }
}
