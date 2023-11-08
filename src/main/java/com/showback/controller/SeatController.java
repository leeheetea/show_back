package com.showback.controller;

import com.showback.dto.SeatDTO;
import com.showback.model.Seat;
import com.showback.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody SeatDTO seatDTO) {

        Seat createdSeat = seatService.createSeat(seatDTO);

        return ResponseEntity.ok(createdSeat);
    }
}
