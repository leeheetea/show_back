package com.showback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {

    private Long showId;

    private String title;

    private String type;

    private List<String> contentDetail;

    private String thumbnailUrl;

    private int price;

    private Long venueId;

    private List<ShowScheduleDTO> showSchedules;

    private List<ShowBannerDTO> showBanners;
}
