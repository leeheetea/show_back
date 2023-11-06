package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ShowDTO;
import com.showback.dto.ShowScheduleDTO;
import com.showback.dto.ShowSeatDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Show;
import com.showback.model.ShowSchedule;
import com.showback.model.ShowSeat;
import com.showback.service.ShowSeatService;
import com.showback.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/show")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    private final ShowSeatService showSeatService;

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody ShowDTO showDTO, BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Show show = showService.createShow(showDTO);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable("showId") Long showId) throws JsonProcessingException {
        ShowDTO showDTO = showService.findShowDTOById(showId);
        if (showDTO == null) {
            throw new ShowNotFoundException(showId);
        }
        return ResponseEntity.ok(showDTO);
    }

    @PostMapping("/{showId}")
    public ResponseEntity<?> updateShow(@RequestBody ShowDTO showDTO, @PathVariable("showId") Long showId) throws JsonProcessingException {

        showService.updateShow(showDTO);
        return ResponseEntity.ok().body(showId);
    }

    @GetMapping("/seat/{showId}")
    public ResponseEntity<?> getShowSeat(
            @PathVariable Long showId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time) {

        List<ShowSeatDTO> showSeats = showSeatService.getShowSeats(showId, date, time);

        return ResponseEntity.ok().body(showSeats);
    }

//    @GetMapping("/banner")
//    public ResponseEntity<?> getShowBanner(
//            @PathVariable Long showId,
//
//    )
}
