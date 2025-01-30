package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.OrderDishRequest;
import com.pragma.powerup.application.dto.OrderDishResponse;
import com.pragma.powerup.application.mapper.OrderDishRequestMapper;
import com.pragma.powerup.application.mapper.OrderDishResponseMapper;
import com.pragma.powerup.domain.api.IOrderDishServicePort;
import com.pragma.powerup.domain.model.OrderDish;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDishHandler implements IOrderDishHandler {

    private final OrderDishRequestMapper orderdishRequestMapper;
    private final OrderDishResponseMapper orderdishResponseMapper;
    private final IOrderDishServicePort orderdishServicePort;

    @Override
    public Page<OrderDishResponse> getOrderDishs(int page, int size, String sortDirection) {
        return orderdishServicePort.getOrderDishs(page, size, sortDirection)
                .map(orderdishResponseMapper::toOrderDishResponse);
    }

    @Override
    public void saveOrderDishInOrderDish(OrderDishRequest orderdishRequest) {
        OrderDish orderdish = orderdishRequestMapper.toOrderDish(orderdishRequest);
        orderdishServicePort.saveOrderDish(orderdish);
    }

    @Override
    public List<OrderDishResponse> getOrderDishFromOrderDish() {
        return orderdishServicePort.getAllOrderDish().stream()
                .map(orderdishResponseMapper::toOrderDishResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDishResponse getOrderDishFromOrderDish(Long orderdishId) {
        OrderDish orderdish = orderdishServicePort.getOrderDishById(orderdishId);
        return orderdishResponseMapper.toOrderDishResponse(orderdish);
    }

    @Override
    public void updateOrderDishInOrderDish(OrderDishRequest orderdishRequest) {
        OrderDish orderdish = orderdishRequestMapper.toOrderDish(orderdishRequest);
        orderdishServicePort.updateOrderDish(orderdish);
    }

    @Override
    public void deleteOrderDishFromOrderDish(Long orderdishId) {
        orderdishServicePort.deleteOrderDish(orderdishId);
    }

}
