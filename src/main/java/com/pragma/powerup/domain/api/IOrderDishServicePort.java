package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderDish;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderDishServicePort {

    void saveOrderDish(OrderDish orderdish);

    List<OrderDish> getAllOrderDish();

    OrderDish getOrderDishById(Long orderdishId);

    void updateOrderDish(OrderDish orderdish);

    void deleteOrderDish(Long orderdishId);

    Page<OrderDish> getOrderDishs(int page, int size, boolean ascending);

    Page<OrderDish> getOrderDishs(int pageNumber, int pageSize, String sortDirection);
}
