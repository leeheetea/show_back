package com.showback.repository;

import com.showback.model.Order;
import com.showback.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByOrder(Order order);

}
