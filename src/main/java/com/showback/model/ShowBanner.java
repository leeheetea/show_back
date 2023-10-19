package com.showback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "shows_banners")
public class ShowBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showBannerId;

    private String bannerUrl;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
