package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ShowDTO;
import com.showback.dto.VenueDTO;
import com.showback.model.Venue;
import com.showback.repository.VenueRepository;
import com.showback.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{venueId}")
    public ResponseEntity<VenueDTO> getVenue(@PathVariable("venueId") Long venueId)
     throws JsonProcessingException {
        VenueDTO venueDTO = venueService.findById(venueId);
        return ResponseEntity.ok().body(venueDTO);
    }
}
