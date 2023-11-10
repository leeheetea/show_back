package com.showback.repository;

import com.showback.model.Order;
import com.showback.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserAuth(UserAuth userAuth);

    List<Order> findAllByUserAuth(UserAuth userAuth);

}
