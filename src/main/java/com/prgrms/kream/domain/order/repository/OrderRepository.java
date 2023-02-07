package com.prgrms.kream.domain.order.repository;

import com.prgrms.kream.domain.order.model.Order;
import com.prgrms.kream.domain.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Modifying
	@Query("update Order o set o.orderStatus = :orderStatus where o.id = :id")
	int updateOrderStatusById(@Param("orderStatus") OrderStatus orderStatus, @Param("id") Long id);
}
