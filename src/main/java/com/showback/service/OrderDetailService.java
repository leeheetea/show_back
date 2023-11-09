package com.showback.service;

import com.showback.dto.OrderDetailDTO;
import com.showback.model.OrderDetail;
import com.showback.model.ShowSeat;
import com.showback.repository.OrderDetailRepository;
import com.showback.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {

        Long showSeatId = orderDetailDTO.getSeatId();
        ShowSeat showSeat = showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new EntityNotFoundException("ShowSeat not found for id: " + showSeatId));

        showSeat.setCanReservation(false);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFinalPrice(orderDetailDTO.getPrice());
        orderDetail.setShowSeat(showSeat);

        orderDetailRepository.save(orderDetail);

        return orderDetail;
    }
}

