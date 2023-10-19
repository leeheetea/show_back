package com.showback.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShowDTO {

    private Long showId;

    private String title;

    private String type;

    private String contentDetail;

    private String thumbnailUrl;

    private int price;

    private VenueDTO venue;

    private List<ShowScheduleDTO> showSchedules;

    private List<ShowBannerDTO> showBanners;
}
