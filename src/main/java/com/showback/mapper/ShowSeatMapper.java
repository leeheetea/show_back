package com.showback.mapper;

import com.showback.dto.SeatDTO;
import com.showback.dto.ShowSeatDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Seat;
import com.showback.model.Show;
import com.showback.model.ShowSeat;
import com.showback.repository.SeatRepository;
import com.showback.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowSeatMapper {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public ShowSeatDTO toDTO(ShowSeat showSeat) {
        ShowSeatDTO dto = new ShowSeatDTO();
        Seat seat = showSeat.getSeat();
        SeatDTO seatDTO = seatMapper.toDTO(seat);


        dto.setShowSeatId(showSeat.getShowSeatId());
        dto.setCanReservation(showSeat.isCanReservation());
        dto.setShowId(showSeat.getShow() != null ? showSeat.getShow().getShowId() : null);
        dto.setSeatDTO(seatDTO);

        return dto;
    }

    public ShowSeat toEntity(ShowSeatDTO dto) {
        ShowSeat showSeat = new ShowSeat();
        SeatDTO seatDTO = dto.getSeatDTO();
        Seat seat = seatMapper.toEntity(seatDTO);

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowNotFoundException(dto.getShowId()));

        showSeat.setShowSeatId(dto.getShowSeatId());
        showSeat.setCanReservation(dto.isCanReservation());
        showSeat.setShow(show);
        showSeat.setSeat(seat);

        return showSeat;
    }

}
