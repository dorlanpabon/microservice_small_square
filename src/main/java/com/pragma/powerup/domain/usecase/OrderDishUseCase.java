package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderDishServicePort;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IOrderDishPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class OrderDishUseCase implements IOrderDishServicePort {

    private final IOrderDishPersistencePort orderdishPersistencePort;

    public OrderDishUseCase(IOrderDishPersistencePort orderdishPersistencePort) {
        this.orderdishPersistencePort = orderdishPersistencePort;
    }

    @Override
    public void saveOrderDish(OrderDish orderdish) {
        orderdishPersistencePort.saveOrderDish(orderdish);
    }

    @Override
    public List<OrderDish> getAllOrderDish() {
        return orderdishPersistencePort.getAllOrderDish();
    }

    @Override
    public OrderDish getOrderDishById(Long orderdishId) {
        return orderdishPersistencePort.getOrderDishById(orderdishId);
    }

    @Override
    public void updateOrderDish(OrderDish orderdish) {
        orderdishPersistencePort.updateOrderDish(orderdish);
    }

    @Override
    public void deleteOrderDish(Long orderdishId) {
        orderdishPersistencePort.deleteOrderDish(orderdishId);
    }

    @Override
    public Page<OrderDish> getOrderDishs(int pageNumber, int pageSize, String sortDirection) {
        Sort sort = Sort.by("name");
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return orderdishPersistencePort.findAll(pageable);
    }

    //TODO: Add pagination support if needed
    public abstract Page<OrderDish> getOrderDishs(int page, int size, boolean ascending);
}
