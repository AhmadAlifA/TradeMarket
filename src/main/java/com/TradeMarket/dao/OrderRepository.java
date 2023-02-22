package com.TradeMarket.dao;

import com.TradeMarket.dto.order.OrderGridDTO;
import com.TradeMarket.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT COUNT(ord.orderId)
        FROM Order AS ord
        WHERE ord.productId = :productId
        """)
    public Long countByProductId(@Param("productId") Long productId);

    @Query("""
            SELECT new com.TradeMarket.dto.order.OrderGridDTO(
                ord.orderId,
                ord.orderDate,
                ord.konsumenName,
                pro.productId,
                ord.quantity,
                pro.priceForBuy
            )
            FROM Order AS ord
                JOIN ord.product as pro
                        WHERE ord.konsumenName LIKE %:searchName%
            """)
    public Page<OrderGridDTO> getOrder(@Param("searchName") String searchName, Pageable pageable);
}
