package com.showback.controller;

import com.showback.dto.ShowDTO;
import com.showback.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.List;

@EnableWebMvc //config 값 세팅
@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final ShowService showService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ShowDTO>> searchShows(@RequestParam(name = "keyword") String keyword,
                                                     @RequestParam(name = "type", required = false) List<String> types) {
        try {
            List<ShowDTO> results = showService.searchShows(keyword, types);
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
