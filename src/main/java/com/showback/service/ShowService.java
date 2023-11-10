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
import org.springframework.data.domain.Pageable;
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
    public List<ShowDTO> findShowDTOByType(String type, Pageable pageable){

        List<Show> shows = showRepository.findByType(type, pageable);

        return shows.stream()
                .map(show -> {
                    try {
                        return showMapper.toDTO(show);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
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

        showRepository.save(show);

        ShowBannerDTO showBanners = showDTO.getShowBanners();
        if(showBanners != null){
            ShowBanner showBanner = showBannerMapper.toEntity(showBanners);
            showBanner.setShow(show);
            showBannerRepository.save(showBanner);
        }

        return show;
    }

    @Transactional
    public Long updateShow(ShowDTO showDTO) throws JsonProcessingException {
        Show show = showRepository.findById(showDTO.getShowId())
                .orElseThrow(() -> new ShowNotFoundException(showDTO.getShowId()));

        show.setTitle(showDTO.getTitle());
        show.setType(showDTO.getType());

        String contentDetail = objectMapper.writeValueAsString(showDTO.getContentDetail());
        show.setContentDetail(contentDetail);
        show.setThumbnailUrl(showDTO.getThumbnailUrl());
        show.setPrice(showDTO.getPrice());
        show.setPeriod(showDTO.getPeriod());
        System.out.println("showDTO.getPeriod() = " + showDTO.getPeriod());

        if (showDTO.getVenueId() != null) {
            Venue venue = venueRepository.findById(showDTO.getVenueId()).orElseThrow(EntityNotFoundException::new);
            show.setVenue(venue);
        }

        List<ShowScheduleDTO> showSchedulesDTO = showDTO.getShowSchedules();
        List<ShowSchedule> newSchedules = new ArrayList<>();
        for (ShowScheduleDTO scheduleDTO : showSchedulesDTO) {
            ShowSchedule newSchedule = new ShowSchedule();
            newSchedule.setScheduleId(scheduleDTO.getScheduleId());
            newSchedule.setScheduleDate(scheduleDTO.getScheduleDate());
            newSchedule.setScheduleTime(scheduleDTO.getScheduleTime());
            newSchedules.add(newSchedule);
        }
        show.setShowSchedules(newSchedules);

        ShowBannerDTO showBanners = showDTO.getShowBanners();
        ShowBanner showBanner = showBannerMapper.toEntity(showBanners);

        show.setShowBanner(showBanner);

        return show.getShowId();
    }


    @Transactional
    public List<ShowDTO> searchShows(String keyword, List<String> types) throws JsonProcessingException {
        List<Show> shows;
        if (types != null && !types.isEmpty()) {
            shows = showRepository.searchShowsByKeywordAndType(keyword, types.get(0));
        } else {
            shows = showRepository.searchShowsByKeyword(keyword);
        }

        List<ShowDTO> results = new ArrayList<>();
        for (Show show : shows) {
            results.add(showMapper.toDTO(show));
        }
        return results;
    }

    private List<ShowDTO> filterShowsByType(List<ShowDTO> results, List<String> types) {
        List<ShowDTO> filteredResults = new ArrayList<>();
        for (ShowDTO show : results) {
            String showType = show.getType();


            if (types.contains(showType)) {
                filteredResults.add(show);
            }
        }
        return filteredResults;
    }

    public List<ShowBannerDTO> findAllShowBanner(Pageable pageable){
        List<ShowBanner> showBanners = showBannerRepository.findByBannerUrlIsNotNullAndNotEmpty(pageable);
        return showBanners.stream()
                .map(showBannerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ShowBannerDTO> findAllSmallBanner(Pageable pageable){
        List<ShowBanner> byBannerUrlIsNotNull = showBannerRepository.findBySmallBannerUrlIsNotNullAndNotEmpty(pageable);
        return byBannerUrlIsNotNull.stream()
                .map(showBannerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
