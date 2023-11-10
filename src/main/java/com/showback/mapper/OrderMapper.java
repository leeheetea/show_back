package com.showback.mapper;

import com.showback.dto.OrderDTO;
import com.showback.dto.ReservationDTO;
import com.showback.model.Order;
import com.showback.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ReservationMapper reservationMapper;

    public OrderDTO toDTO(Order order){

        Reservation reservation = order.getReservation();
        ReservationDTO reservationDTO = reservationMapper.toDTO(reservation);


        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderState(order.getOrderState());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTicketAmount(order.getTicketAmount());
        orderDTO.setUserAuthId(order.getUserAuth().getAuthId());
        orderDTO.setReservation(reservationDTO);

        return orderDTO;
    }

}
