package com.showback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "venues")
public class Venue {

    @Id
    private Long venueId;

    private String venueName;

    private String venueAddress;

    private int seatMaxRow;

    private int seatMaxColumn;

    @OneToMany(mappedBy = "venue")
    @JsonBackReference
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "venue")
    @JsonBackReference
    private List<Show> shows = new ArrayList<>();

    public void addShow(Show show) {
        shows.add(show);
        show.setVenue(this);
    }
}
