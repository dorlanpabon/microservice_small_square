package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderDish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderDishPersistencePort {

    void saveOrderDish(OrderDish orderdish);

    List<OrderDish> getAllOrderDish();

    OrderDish getOrderDishById(Long orderdishId);

    void updateOrderDish(OrderDish orderdish);

    void deleteOrderDish(Long orderdishId);

    Page<OrderDish> getOrderDishs(int page, int size, boolean ascending);

    Page<OrderDish> getOrderDishs(int pageNumber, int pageSize, String sortDirection);

    Page<OrderDish> getOrderDishs(PageRequest pageRequest);

    Page<OrderDish> findAll(Pageable pageable);
}
