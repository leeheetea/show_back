package com.showback.controller;

import com.showback.dto.ReservationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/orderDetail"))
@RequiredArgsConstructor
public class OrerDetailController {

    public ReservationResponseDTO getOrderDetail(Long OrderId){

        return null;

    }
}
