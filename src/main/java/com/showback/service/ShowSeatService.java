package com.showback.service;

import com.showback.dto.ShowSeatDTO;
import com.showback.mapper.ShowSeatMapper;
import com.showback.model.ShowSeat;
import com.showback.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatMapper showSeatMapper;

    public Long createShowSeat(ShowSeatDTO showSeatDTO, Long showId){

        ShowSeat showSeat = showSeatMapper.toEntity(showSeatDTO);


        showSeatRepository.save(showSeat);

        return 1L;
    }

    public List<ShowSeatDTO> getShowSeats(Long showId, LocalDate date, LocalTime time){

        List<ShowSeat> showSeatsByShowAndSchedule =
                showSeatRepository.findShowSeatsByShowAndSchedule(showId, date, time);

        return showSeatsByShowAndSchedule.stream()
                .map(showSeatMapper::toDTO)
                .collect(Collectors.toList());
    }
}
