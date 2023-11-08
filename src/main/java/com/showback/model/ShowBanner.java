package com.showback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    @JsonBackReference
    private Show show;
}
