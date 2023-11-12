package com.showback.service;

import com.showback.domain.OrderState;
import com.showback.dto.OrderDTO;
import com.showback.dto.OrderRequestDTO;
import com.showback.mapper.OrderMapper;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserAuthRepository userAuthRepository;
    private final OrderDetailService orderDetailService;
    private final OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(OrderRequestDTO orderRequestDTO) {

        UserAuth userAuth = userAuthRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("UserAuth not found for id: " + orderRequestDTO.getUserId()));

        List<SelectedSeatsDTO> selectedSeats = orderRequestDTO.getSelectedSeats();

        Order order = new Order();
        order.setTicketAmount(selectedSeats.size());
        order.setOrderState(OrderState.PENDING);
        order.setOrderDate(orderRequestDTO.getDate());
        order.setUserAuth(userAuth);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (SelectedSeatsDTO selectedSeatDTO : selectedSeats) {
            OrderDetail orderDetail = orderDetailService
                    .createOrderDetail(selectedSeatDTO.toOrderDetailDTO());
            order.addOrderDetail(orderDetail);
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public List<OrderDTO> findByOrder(Long userId){

        UserAuth userAuth = userAuthRepository.findByUserId(userId);
        List<Order> orderList = orderRepository.findAllByUserAuth(userAuth);

        return orderList.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());

    }
}
