package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ShowDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Show;
import com.showback.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/show")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody ShowDTO showDTO, BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Show show = showService.createShow(showDTO);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable Long showId) throws JsonProcessingException {
        ShowDTO showDTO = showService.findShowDTOById(showId);
        if (showDTO == null) {
            throw new ShowNotFoundException(showId);
        }
        return ResponseEntity.ok(showDTO);
    }

    @PostMapping("/{showId}")
    public ResponseEntity<?> updateShow(@RequestBody ShowDTO showDTO) throws JsonProcessingException {

        Long showId = showService.updateShow(showDTO);
        return ResponseEntity.ok().body(showId);
    }
}
