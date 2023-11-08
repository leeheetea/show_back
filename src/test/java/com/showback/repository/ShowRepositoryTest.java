package com.showback.repository;

import com.showback.model.Show;
import com.showback.model.ShowSchedule;
import com.showback.model.Venue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@TestPropertySource("classpath:application-test.properties")
class ShowRepositoryTest {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ShowScheduleRepository showScheduleRepository;

    private Show show;

    @BeforeEach
    public void setUp() {
        Venue venue = new Venue();
        venue.setVenueId(1L);
        venue.setVenueAddress("신림동");
        venue.setVenueName("신림동 뭐시기");
        venueRepository.save(venue);

        show = new Show();
        show.setTitle("Test Show");
        show.setType("Musical");
        show.setContentDetail("Test Content Detail");
        show.setThumbnailUrl("http://test.com/test.jpg");
        show.setPrice(100);
        show.setVenue(venue);

        ArrayList<ShowSchedule> showSchedules = new ArrayList<>();
        ShowSchedule showSchedule = new ShowSchedule();
        showSchedule.setScheduleDate(LocalDate.parse("2023-10-12"));
        showSchedule.setScheduleTime(LocalTime.parse("10:00"));

        showSchedule.setShow(show);

        showSchedules.add(showSchedule);
        show.setShowSchedules(showSchedules);

        showRepository.save(show);
    }

    @AfterEach
    public void tearDown() {
        showRepository.delete(show);
    }

    @Test
    void findByShowId_ShouldReturnShow() {
        Show foundShow = showRepository.findById(show.getShowId()).orElseThrow(EntityExistsException::new);
        ShowSchedule showSchedule = showScheduleRepository.findByShow(foundShow);


        assertThat(foundShow).isNotNull();
        assertThat(foundShow.getTitle()).isEqualTo(show.getTitle());
        assertThat(foundShow.getType()).isEqualTo(show.getType());
        assertThat(foundShow.getShowId()).isEqualTo(showSchedule.getShow().getShowId());

    }
}
