package com.showback.repository;

import com.showback.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT count(od) FROM OrderDetail od WHERE od.order.orderId = :orderId")
    int countByOrderId(@Param("orderId") Long orderId);
}
