package com.showback.mapper;

import com.showback.dto.ShowScheduleDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Show;
import com.showback.model.ShowSchedule;
import com.showback.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowScheduleMapper {

    private final ShowRepository showRepository;

    // DTO -> Entity 변환
    public ShowSchedule toEntity(ShowScheduleDTO dto) {
        if (dto == null) {
            return null;
        }

        ShowSchedule showSchedule = new ShowSchedule();
        showSchedule.setScheduleId(dto.getScheduleId());
        showSchedule.setScheduleDate(dto.getScheduleDate());
        showSchedule.setScheduleTime(dto.getScheduleTime());

        // Show 엔터티 참조 설정
        if (dto.getShowId() != null) {
            Show show = showRepository.findById(dto.getShowId())
                    .orElseThrow(() -> new ShowNotFoundException(dto.getShowId()));
            showSchedule.setShow(show);
        }
        return showSchedule;
    }

    // Entity -> DTO 변환
    public ShowScheduleDTO toDTO(ShowSchedule showSchedule) {
        if (showSchedule == null) {
            return null;
        }

        ShowScheduleDTO showScheduleDTO = new ShowScheduleDTO();
        showScheduleDTO.setScheduleId(showSchedule.getScheduleId());
        showScheduleDTO.setScheduleDate(showSchedule.getScheduleDate());
        showScheduleDTO.setScheduleTime(showSchedule.getScheduleTime());
        if(showSchedule.getShow() != null){
            showScheduleDTO.setShowId(showSchedule.getShow().getShowId());
        }

        return showScheduleDTO;
    }
}
