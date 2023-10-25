package com.showback.mapper;

import com.showback.dto.ShowSeatDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Seat;
import com.showback.model.Show;
import com.showback.model.ShowSeat;
import com.showback.repository.SeatRepository;
import com.showback.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ShowSeatMapper {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public ShowSeatDTO toDTO(ShowSeat showSeat) {
        ShowSeatDTO dto = new ShowSeatDTO();

        dto.setShowSeatId(showSeat.getShowSeatId());
        dto.setCanReservation(showSeat.isCanReservation());
        dto.setShowId(showSeat.getShow() != null ? showSeat.getShow().getShowId() : null);
        dto.setSeatId(showSeat.getSeat() != null ? showSeat.getSeat().getSeatId() : null);

        return dto;
    }

    public ShowSeat toEntity(ShowSeatDTO dto) {
        ShowSeat showSeat = new ShowSeat();

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowNotFoundException(dto.getShowId()));

        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(EntityNotFoundException::new);

        showSeat.setShowSeatId(dto.getShowSeatId());
        showSeat.setCanReservation(dto.isCanReservation());
        showSeat.setShow(show);
        showSeat.setSeat(seat);

        return showSeat;
    }

}
