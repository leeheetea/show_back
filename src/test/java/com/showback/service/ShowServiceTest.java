package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ShowDTO;
import com.showback.model.Show;
import com.showback.model.ShowBanner;
import com.showback.model.ShowSchedule;
import com.showback.repository.ShowRepository;
import com.showback.repository.ShowScheduleRepository;
import com.showback.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class ShowServiceTest {

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowRepository showRepository;

    @Test
    void testFindShowDTOById() throws JsonProcessingException {
        Show show = new Show();
        show.setTitle("Test Show");
        Show savedShow = showRepository.save(show);

        ShowDTO result = showService.findShowDTOById(savedShow.getShowId());
        assertThat(result.getTitle()).isEqualTo("Test Show");
    }

    @Test
    void testCreateShow() throws JsonProcessingException {
        ShowDTO showDTO = new ShowDTO();
        showDTO.setTitle("New Show");

        Show result = showService.createShow(showDTO);
        assertThat(result.getTitle()).isEqualTo("New Show");
    }

    @Test
    void testCreateShowSchedule() {
        Show show = new Show();
        show.setTitle("Test Show");
        Show savedShow = showRepository.save(show);

        ShowSchedule showSchedule = new ShowSchedule();
        showSchedule.setShow(savedShow);

        ShowSchedule result = showService.createShowSchedule(savedShow.getShowId(), showSchedule);
        assertThat(result.getShow().getShowId()).isEqualTo(savedShow.getShowId());
    }

    @Test
    void testCreateShowBanner() {
        Show show = new Show();
        show.setTitle("Test Show");
        Show savedShow = showRepository.save(show);

        ShowBanner showBanner = new ShowBanner();
        showBanner.setShow(savedShow);

        ShowBanner result = showService.createShowBanner(savedShow.getShowId(), showBanner);
        assertThat(result.getShow().getShowId()).isEqualTo(savedShow.getShowId());
    }
}


