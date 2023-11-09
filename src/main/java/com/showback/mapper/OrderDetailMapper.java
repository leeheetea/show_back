package com.showback.mapper;

import com.showback.dto.OrderDetailDTO;
import com.showback.dto.ShowSeatDTO;
import com.showback.model.Order;
import com.showback.model.OrderDetail;
import com.showback.model.Seat;
import com.showback.model.ShowSeat;
import com.showback.repository.OrderRepository;
import com.showback.repository.SeatRepository;
import com.showback.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDetailMapper {

    private final OrderRepository orderRepository;
    private final ShowSeatMapper showSeatMapper;
    private final ShowSeatRepository showSeatRepository;

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO){

        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Order with ID " + orderDetailDTO.getOrderId() + " not found."));

        Long seatId = orderDetailDTO.getSeatId();
        ShowSeat showSeat = showSeatRepository.findById(seatId)
                .orElseThrow(EntityNotFoundException::new);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
        orderDetail.setFinalPrice(orderDetailDTO.getPrice());
        orderDetail.setOrder(order);
        orderDetail.setShowSeat(showSeat);

        return orderDetail;
    }

    public OrderDetailDTO toDTO(OrderDetail orderDetail) {

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailDTO.setPrice(orderDetail.getFinalPrice());
        orderDetailDTO.setOrderId(orderDetail.getOrder().getOrderId());

        return orderDetailDTO;
    }
}
