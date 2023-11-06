package com.showback.controller;

import com.showback.dto.VenueDTO;
import com.showback.model.Venue;
import com.showback.repository.VenueRepository;
import com.showback.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venue")
@RequiredArgsConstructor
public class VenueController {

    private final VenueRepository venueRepository;
    private final VenueService venueService;

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody VenueDTO venueDTO){

        Venue venue = venueService.createVenueWithSeats(venueDTO);

        return ResponseEntity.ok().body(venue);
    }

    
}
