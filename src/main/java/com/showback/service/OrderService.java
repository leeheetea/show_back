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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserAuthRepository userAuthRepository;
    private final OrderDetailService orderDetailService;

    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(OrderRequestDTO orderRequestDTO) {

        UserAuth userAuth = userAuthRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("UserAuth not found for id: " + orderRequestDTO.getUserId()));

        List<SelectedSeatsDTO> selectedSeats = orderRequestDTO.getSelectedSeats();

<<<<<<< HEAD
       return null;
=======
        Order order = new Order();
        order.setTicketAmount(selectedSeats.size());
        order.setOrderState(OrderState.PENDING);
        order.setOrderDate(LocalDate.now());
        order.setUserAuth(userAuth);

        for (SelectedSeatsDTO selectedSeatDTO : selectedSeats) {
            OrderDetail orderDetail = orderDetailService
                    .createOrderDetail(selectedSeatDTO.toOrderDetailDTO());
            order.addOrderDetail(orderDetail);  // 여기서 orderDetail의 order가 설정됩니다.
        }

        orderRepository.save(order);

        return order;
>>>>>>> e850a8de59c2c64fd2e344482c803febaa17c56e
    }
}
