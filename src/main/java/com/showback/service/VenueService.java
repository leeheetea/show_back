package com.showback.service;

import com.showback.dto.VenueDTO;
import com.showback.mapper.VenueMapper;
import com.showback.model.Seat;
import com.showback.model.Venue;
import com.showback.repository.SeatRepository;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;
    private final SeatRepository seatRepository;

    public Venue createVenue(VenueDTO venueDTO){

        Venue venue = venueMapper.toEntity(venueDTO);

        return venueRepository.save(venue);
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

    @Transactional
    public VenueDTO findByVenue(Long venueId){
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(EntityNotFoundException::new);

        return venueMapper.toDTO(venue);
    }
}
