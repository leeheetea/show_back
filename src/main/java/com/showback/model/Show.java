package com.showback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shows")
@Getter
@Setter
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    private String title;

    private String type;

    private String contentDetail;

    private String thumbnailUrl;

    private String period;

    private int price;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    @JsonManagedReference
    private Venue venue;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShowSeat> showSeats = new ArrayList<>();

    @OneToMany(mappedBy = "show")
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowSchedule> showSchedules = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowBanner> showBanners = new ArrayList<>();
}
