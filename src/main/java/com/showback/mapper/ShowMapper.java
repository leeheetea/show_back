package com.showback.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showback.dto.ShowDTO;
import com.showback.model.Show;
import com.showback.model.ShowBanner;
import com.showback.model.ShowSchedule;
import com.showback.model.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowMapper {

    private final ShowScheduleMapper showScheduleMapper;
    private final ShowBannerMapper showBannerMapper;
    private final ObjectMapper objectMapper;

    public Show toEntity(ShowDTO showDTO, Venue venue) throws JsonProcessingException {
        if (showDTO == null) {
            return null;
        }

        String contentDetailJson = objectMapper.writeValueAsString(showDTO.getContentDetail());

        Show show = new Show();
        show.setShowId(showDTO.getShowId());
        show.setTitle(showDTO.getTitle());
        show.setType(showDTO.getType());
        show.setContentDetail(contentDetailJson);
        show.setThumbnailUrl(showDTO.getThumbnailUrl());
        show.setPeriod(showDTO.getPeriod());
        show.setPrice(showDTO.getPrice());

        // VenueDTO를 Venue 엔터티로 변환
        if (venue != null) {
            show.setVenue(venue);
        }

        // ShowSchedule과 연관관계 매핑
        if (showDTO.getShowSchedules() != null) {

            List<ShowSchedule> schedules = showDTO.getShowSchedules().stream()
                    .map(showScheduleMapper::toEntity)
                    .collect(Collectors.toList());
            schedules.forEach(schedule -> schedule.setShow(show));
            show.setShowSchedules(schedules);
        }

        // ShowBanner와 연관관계 매핑
        if (showDTO.getShowBanners() != null) {
            List<ShowBanner> showBanners = showDTO.getShowBanners().stream()
                    .map(showBannerMapper::toEntity)
                    .collect(Collectors.toList());
            showBanners.forEach(showbanner -> showbanner.setShow(show));
            show.setShowBanners(showBanners);
        }

        return show;
    }

    public ShowDTO toDTO(Show show) throws JsonProcessingException {
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
                .showSchedules(show.getShowSchedules().stream()
                        .map(showScheduleMapper::toDTO)
                        .collect(Collectors.toList()))
                .showBanners(show.getShowBanners().stream()
                        .map(showBannerMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
