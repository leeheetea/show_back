package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "venues")
public class Venue {

    @Id
    private Long venueId;

    private String venueName;

    private String venueAddress;

    private int seatMaxRow;

    private int seatMaxColumn;

    @OneToMany(mappedBy = "venue")
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "venue")
    private List<Show> shows = new ArrayList<>();
}
