package com.showback.service;

import com.showback.model.Venue;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    public Venue createVenue(Venue venue){
        return venueRepository.save(venue);
    }
}
