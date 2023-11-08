package com.showback.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showback.dto.ShowBannerDTO;
import com.showback.dto.ShowDTO;
import com.showback.model.Show;
import com.showback.model.ShowBanner;
import com.showback.model.ShowSchedule;
import com.showback.model.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowMapper {

    private final ShowScheduleMapper showScheduleMapper;
    private final ShowBannerMapper showBannerMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public Show toEntity(ShowDTO showDTO, Venue venue) throws JsonProcessingException {


        String contentDetailJson = objectMapper.writeValueAsString(showDTO.getContentDetail());

        ShowBannerDTO showBanners = showDTO.getShowBanners();
        ShowBanner showBanner = showBannerMapper.toEntity(showBanners);

        Show show = new Show();
        show.setShowId(showDTO.getShowId());
        show.setTitle(showDTO.getTitle());
        show.setType(showDTO.getType());
        show.setContentDetail(contentDetailJson);
        show.setThumbnailUrl(showDTO.getThumbnailUrl());
        show.setPeriod(showDTO.getPeriod());
        show.setPrice(showDTO.getPrice());
        show.setShowBanner(showBanner);


        if (showDTO.getShowSchedules() != null) {

            List<ShowSchedule> schedules = showDTO.getShowSchedules().stream()
                    .map(showScheduleMapper::toEntity)
                    .collect(Collectors.toList());
            schedules.forEach(schedule -> schedule.setShow(show));
            show.setShowSchedules(schedules);
        }

        if (venue != null) {
            show.setVenue(venue);
            venue.addShow(show);
        }

        return show;
    }

    @Transactional
    public ShowDTO toDTO(Show show) throws JsonProcessingException {

        ShowBanner showBanner = show.getShowBanner();
        ShowBannerDTO showBannerDTO = showBannerMapper.toDTO(showBanner);

        if (show == null) {
            return null;
        }

        List<String> contentDetail = objectMapper.readValue(show.getContentDetail(), new TypeReference<List<String>>() {
        });

        return ShowDTO.builder()
                .showId(show.getShowId())
                .title(show.getTitle())
                .type(show.getType())
                .contentDetail(contentDetail)
                .thumbnailUrl(show.getThumbnailUrl())
                .price(show.getPrice())
                .period(show.getPeriod())
                .venueId(show.getVenue().getVenueId())
                .venueName(show.getVenue().getVenueName())
                .showSchedules(show.getShowSchedules().stream()
                        .map(showScheduleMapper::toDTO)
                        .collect(Collectors.toList()))
                .showBanners(showBannerDTO)
                .build();
    }
}
