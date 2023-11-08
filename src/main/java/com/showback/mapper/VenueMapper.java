package com.showback.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.VenueDTO;
import com.showback.model.Seat;
import com.showback.model.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VenueMapper {

    private final SeatMapper seatMapper;
    private final ShowMapper showMapper;

    public Venue toEntity(VenueDTO venueDTO){

        if(venueDTO == null){
            return null;
        }

        Venue venue = new Venue();

        if(venueDTO.getVenueId() != null){
            venue.setVenueId(venueDTO.getVenueId());
        }

        venue.setVenueName(venueDTO.getVenueName());
        venue.setVenueAddress(venueDTO.getVenueAddress());
        venue.setSeatMaxRow(venueDTO.getSeatMaxRow());
        venue.setSeatMaxColumn(venueDTO.getSeatMaxColumn());

        if( venueDTO.getSeats() != null){
            venueDTO.getSeats()
                    .forEach(seatDTO -> venue.getSeats()
                            .add(seatMapper.toEntity(seatDTO)));
        }

        if( venueDTO.getShows() != null ){
            venueDTO.getShows()
                    .forEach(showDTO -> {
                        try {
                            venue.getShows()
                                    .add(showMapper.toEntity(showDTO, venue));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        return venue;
    }

    public VenueDTO toDTO(Venue venue){

        VenueDTO venueDTO = new VenueDTO();
        venueDTO.setVenueId(venue.getVenueId());
        venueDTO.setVenueName(venue.getVenueName());
        venueDTO.setVenueAddress(venue.getVenueAddress());
        venueDTO.setSeatMaxRow(venue.getSeatMaxRow());
        venueDTO.setSeatMaxColumn(venue.getSeatMaxColumn());


        if (venue.getSeats() != null){
            venueDTO.setSeats(
                    venue.getSeats().stream()
                            .map(seatMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        if (venue.getShows() != null){
            venueDTO.setShows(
                    venue.getShows().stream()
                            .map(show -> {
                                try {
                                    return showMapper.toDTO(show);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toList())
            );
        }

        return venueDTO;
    }
}
