package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ShowBannerDTO;
import com.showback.dto.ShowDTO;
import com.showback.dto.ShowSeatDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Show;
import com.showback.repository.ShowRepository;
import com.showback.service.ShowSeatService;
import com.showback.service.ShowService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private final ShowRepository showRepository;

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody ShowDTO showDTO, BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Show show = showService.createShow(showDTO);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping
    public ResponseEntity<List<ShowDTO>> getShowByType(@RequestParam String type,
                                                       @RequestParam int page,
                                                       @RequestParam int size) throws JsonProcessingException {

        Pageable pageable = PageRequest.of(page, size);

        List<ShowDTO> showDTOByType = showService.findShowDTOByType(type, pageable);
        long totalCount = showRepository.countByType(type);

        return ResponseEntity.ok()
                .header("totalcount", String.valueOf(totalCount))
                .body(showDTOByType);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable("showId") Long showId
            ) throws JsonProcessingException {

        ShowDTO showDTO = showService.findShowDTOById(showId);
        if (showDTO == null) {
            throw new ShowNotFoundException(showId);
        }
        return ResponseEntity.ok(showDTO);
    }

    @PostMapping("/{showId}")
    public ResponseEntity<?> updateShow(@RequestBody ShowDTO showDTO,
                                        @PathVariable("showId") Long showId) throws JsonProcessingException {

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

    @GetMapping("/banner")
    public ResponseEntity<?> getShowBanner(
            @RequestParam int page,
            @RequestParam int size
    ){
        Pageable pageable = PageRequest.of(page, size);

        List<ShowBannerDTO> allShowBanner = showService.findAllShowBanner(pageable);

        return ResponseEntity.ok().body(allShowBanner);
    }

    @GetMapping("/small-banner")
    public ResponseEntity<?> getShowSmallBanner(
            @RequestParam int page,
            @RequestParam int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        List<ShowBannerDTO> allShowBanner = showService.findAllSmallBanner(pageable);

        return ResponseEntity.ok().body(allShowBanner);
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<?> deleteShow(@PathVariable Long showId){

        Show show = showRepository.findById(showId).orElseThrow(() -> new ShowNotFoundException(showId));
        showRepository.delete(show);

        return ResponseEntity.ok().body(showId + "번 공연이 삭제되었습니다.");
    }
}
