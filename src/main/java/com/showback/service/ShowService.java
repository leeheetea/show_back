package com.showback.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showback.dto.ShowBannerDTO;
import com.showback.dto.ShowDTO;
import com.showback.dto.ShowScheduleDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.mapper.ShowBannerMapper;
import com.showback.mapper.ShowMapper;
import com.showback.mapper.ShowScheduleMapper;
import com.showback.model.*;
import com.showback.repository.ShowBannerRepository;
import com.showback.repository.ShowRepository;
import com.showback.repository.ShowScheduleRepository;
import com.showback.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final VenueRepository venueRepository;
    private final ShowBannerRepository showBannerRepository;
    private final ShowScheduleRepository showScheduleRepository;

    private final ShowMapper showMapper;
    private final ShowScheduleMapper showScheduleMapper;
    private final ShowBannerMapper showBannerMapper;

    private final ObjectMapper objectMapper;


    @Transactional
    public ShowDTO findShowDTOById(Long showId) throws JsonProcessingException {
        Show foundShow = showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFoundException(showId));

        return showMapper.toDTO(foundShow);
    }

    @Transactional
    public Show createShow(ShowDTO showDTO) throws JsonProcessingException {

        Venue venue = null;
        if(showDTO.getVenueId() != null) {
            venue = venueRepository.findById(showDTO.getVenueId())
                    .orElseThrow(() -> new EntityNotFoundException("Venue not found with ID: " + showDTO.getVenueId()));
        }

        Show show = showMapper.toEntity(showDTO, venue);

        if (venue != null && venue.getSeats() != null) {
            List<ShowSeat> showSeats = new ArrayList<>();
            for (Seat seat : venue.getSeats()) {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setSeat(seat);
                showSeat.setShow(show);
                showSeats.add(showSeat);
            }
            show.setShowSeats(showSeats);
        }

        return showRepository.save(show);
    }

    @Transactional
    public Long updateShow(ShowDTO showDTO) throws JsonProcessingException{
        Show show = showRepository.findById(showDTO.getShowId())
                .orElseThrow(() -> new ShowNotFoundException(showDTO.getShowId()));

        show.setTitle(showDTO.getTitle());
        show.setType(showDTO.getType());

        String contentDetail = objectMapper.writeValueAsString(showDTO.getContentDetail());
        show.setContentDetail(contentDetail);
        show.setThumbnailUrl(showDTO.getThumbnailUrl());
        show.setPrice(showDTO.getPrice());
        show.setPeriod(showDTO.getPeriod());

        if(showDTO.getVenueId() != null){
            Venue venue = venueRepository.findById(showDTO.getVenueId()).orElseThrow(EntityNotFoundException::new);
            show.setVenue(venue);
        }

        List<ShowSchedule> schedules = new ArrayList<>();

        for (ShowScheduleDTO dto : showDTO.getShowSchedules()) {
            if (dto.getShowId() != null) {
                ShowSchedule Schedule = showScheduleRepository.findById(dto.getShowId())
                        .orElseThrow(() -> new ShowNotFoundException(dto.getShowId()));
                schedules.add(Schedule);
            } else {
                schedules.add(showScheduleMapper.toEntity(dto));
            }
        }

        ShowBannerDTO showBanners = showDTO.getShowBanners();
        ShowBanner showBanner = showBannerMapper.toEntity(showBanners);

        show.setShowBanner(showBanner);

        return show.getShowId();
    }

    @Transactional
    public List<ShowBannerDTO> findAllShowBanner(){
        List<ShowBanner> showBanners = showBannerRepository.findAll();
        return showBanners.stream()
                .map(showBannerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ShowBannerDTO> findAllSmallBanner(){
        List<ShowBanner> byBannerUrlIsNotNull = showBannerRepository.findByBannerUrlIsNotNull();
        return byBannerUrlIsNotNull.stream()
                .map(showBannerMapper::toDTO)
                .collect(Collectors.toList());
    }

}
