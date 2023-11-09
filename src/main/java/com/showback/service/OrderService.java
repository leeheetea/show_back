package com.showback.service;

import com.showback.domain.OrderState;
import com.showback.dto.OrderRequestDTO;
import com.showback.model.Order;
import com.showback.model.OrderDetail;
import com.showback.model.SelectedSeatsDTO;
import com.showback.model.UserAuth;
import com.showback.repository.OrderRepository;
import com.showback.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserAuthRepository userAuthRepository;
    private final OrderDetailService orderDetailService;

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        UserAuth userAuth = userAuthRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("UserAuth not found for id: " + orderRequestDTO.getUserId()));

        Order order = new Order();
        order.setTicketAmount(orderRequestDTO.getSelectedSeats().size());
        order.setOrderState(OrderState.PENDING);
        order.setOrderDate(new Date());
        order.setUserAuth(userAuth);

        for (SelectedSeatsDTO selectedSeatDTO : orderRequestDTO.getSelectedSeats()) {
            OrderDetail orderDetail = orderDetailService
                    .createOrderDetail(selectedSeatDTO.toOrderDetailDTO());
            orderDetail.setOrder(order);
            order.addOrderDetail(orderDetail);
        }

        orderRepository.save(order);

        return order;
    }
}
