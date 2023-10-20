package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "shows_banners")
public class ShowBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showBannerId;

    private String bannerUrl;

    private String smallBannerUrl;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
