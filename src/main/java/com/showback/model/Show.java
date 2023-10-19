package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shows")
@Data
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    private String title;

    private String type;

    private String contentDetail;

    private String thumbnailUrl;

    private int price;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "show")
    private List<ShowSeat> showSeats = new ArrayList<>();

    @OneToMany(mappedBy = "show")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "show")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "show")
    private List<ShowSchedule> showSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "show")
    private List<ShowBanner> showBanners = new ArrayList<>();
}
