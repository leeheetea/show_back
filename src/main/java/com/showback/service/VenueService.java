package com.showback.service;

import com.showback.dto.VenueDTO;
import com.showback.mapper.VenueMapper;
import com.showback.model.Venue;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    public Venue createVenue(VenueDTO venueDTO){

        Venue venue = venueMapper.toEntity(venueDTO);

        return venueRepository.save(venue);
    }
}
