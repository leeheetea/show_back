package com.showback.repository;

import com.showback.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    Venue findByVenueId(Long venueId);
}
