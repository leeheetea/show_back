package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ShowDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchTest {
    @Autowired
    private ShowService showService;

    @Test
    public void testSearchShow() throws JsonProcessingException{
        List<ShowDTO> list = showService.searchShows("벤",null);
        System.out.println("list ====================================================================== " + list);
    }
}
