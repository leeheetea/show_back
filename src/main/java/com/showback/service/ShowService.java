package com.showback.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showback.dto.ShowBannerDTO;
import com.showback.dto.ShowDTO;
import com.showback.dto.ShowScheduleDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.mapper.ShowBannerMapper;
import com.showback.mapper.ShowMapper;
import com.showback.mapper.ShowScheduleMapper;
import com.showback.model.Show;
import com.showback.model.ShowBanner;
import com.showback.model.ShowSchedule;
import com.showback.model.Venue;
import com.showback.repository.ShowBannerRepository;
import com.showback.repository.ShowRepository;
import com.showback.repository.ShowScheduleRepository;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowScheduleRepository showScheduleRepository;
    private final ShowBannerRepository showBannerRepository;
    private final VenueRepository venueRepository;

    private final ShowMapper showMapper;
    private final ShowScheduleMapper showScheduleMapper;
    private final ShowBannerMapper showBannerMapper;

    private final ObjectMapper objectMapper;




    private List<ShowScheduleDTO> convertToScheduleDTOs(List<ShowSchedule> schedules) {
        return schedules.stream().map(showScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    private List<ShowBannerDTO> convertToBannerDTOs(List<ShowBanner> banners) {
        return banners.stream().map(showBannerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShowDTO findShowDTOById(Long showId) throws JsonProcessingException {
        Show foundShow = showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFoundException(showId));

        List<ShowScheduleDTO> showScheduleDTOs = convertToScheduleDTOs(foundShow.getShowSchedules());
        List<ShowBannerDTO> showBannerDTOs = convertToBannerDTOs(foundShow.getShowBanners());

        List<String> contentDetail = objectMapper
                .readValue(foundShow.getContentDetail(), new TypeReference<List<String>>() {
        });

        return ShowDTO.builder()
                .showId(foundShow.getShowId())
                .title(foundShow.getTitle())
                .type(foundShow.getType())
                .contentDetail(contentDetail)
                .thumbnailUrl(foundShow.getThumbnailUrl())
                .price(foundShow.getPrice())
                .showSchedules(showScheduleDTOs)
                .showBanners(showBannerDTOs)
                .build();
    }

    @Transactional
    public Show createShow(ShowDTO showDTO) throws JsonProcessingException {

        Venue venue = null;
        if(showDTO.getVenueId() != null) {
            venue = venueRepository.findById(showDTO.getVenueId())
                    .orElseThrow(() -> new EntityNotFoundException("Venue not found with ID: " + showDTO.getVenueId()));
        }

        Show show = showMapper.toEntity(showDTO, venue);
        return showRepository.save(show);
    }

//    @Transactional
//    public ShowSchedule createShowSchedule(Long showId, ShowSchedule showSchedule){
//        Show show = showRepository.findById(showId)
//                .orElseThrow(() -> new ShowNotFoundException(showId));
//
//        showSchedule.setShow(show);
//        return showScheduleRepository.save(showSchedule);
//    }
//
//    @Transactional
//    public ShowBanner createShowBanner(Long showId, ShowBanner showBanner){
//        Show show = showRepository.findById(showId)
//                .orElseThrow(() -> new ShowNotFoundException(showId));
//
//        showBanner.setShow(show);
//        return showBannerRepository.save(showBanner);
//    }
//
}
