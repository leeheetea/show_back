package com.showback.mapper;

import com.showback.dto.ShowBannerDTO;
import com.showback.exception.ShowNotFoundException;
import com.showback.model.Show;
import com.showback.model.ShowBanner;
import com.showback.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowBannerMapper {

    private final ShowRepository showRepository;

    public ShowBanner toEntity(ShowBannerDTO dto) {
        if (dto == null) {
            return null;
        }
        ShowBanner showBanner = new ShowBanner();
        showBanner.setShowBannerId(dto.getShowBannerId());
        showBanner.setBannerUrl(dto.getBannerUrl());
        showBanner.setSmallBannerUrl(dto.getSmallBannerUrl());

        return showBanner;
    }

    public ShowBannerDTO toDTO(ShowBanner showBanner) {
        if (showBanner == null) {
            return null;
        }
        ShowBannerDTO dto = new ShowBannerDTO();
        dto.setShowBannerId(showBanner.getShowBannerId());
        dto.setBannerUrl(showBanner.getBannerUrl());
        dto.setSmallBannerUrl(showBanner.getSmallBannerUrl());
        if (showBanner.getShow() != null) {
            dto.setShowId(showBanner.getShow().getShowId());
        }
        return dto;
    }
}
