package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IMessageServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserServicePort userServicePort;
    private final IEmployeePersistencePort employeePersistencePort;
    private final IMessageServicePort messageServicePort;

    public OrderUseCase(
            IOrderPersistencePort orderPersistencePort,
            IUserServicePort userServicePort,
            IEmployeePersistencePort employeePersistencePort,
            IMessageServicePort messageServicePort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.userServicePort = userServicePort;
        this.employeePersistencePort = employeePersistencePort;
        this.messageServicePort = messageServicePort;
    }

    @Override
    public void saveOrder(Order order) {
        order.setClientId(userServicePort.getUserId());
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.PENDING.toString());

        Long count = orderPersistencePort.countByClientIdAndStatus(
                order.getClientId(),
                List.of(OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION, OrderStatusEnum.READY)
        );

        if (count > 0) {
            throw new DomainException(DomainConstants.ORDER_ALREADY_EXISTS);
        }

        orderPersistencePort.saveOrder(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderPersistencePort.getAllOrder();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderPersistencePort.getOrderById(orderId);
    }

    @Override
    public void updateOrder(Order order) {
        Long userId = userServicePort.getUserId();
        Employee employee = employeePersistencePort.getByEmployeeId(userId);
        if (employee == null){
            throw new DomainException(DomainConstants.EMPLOYEE_NOT_FOUND);
        }
        if(employee.getRestaurant() == null) {
            throw new DomainException(DomainConstants.RESTAURANT_NOT_FOUND);
        }

        Long restaurantId = employee.getRestaurant().getId();
        Order orderUpdated = orderPersistencePort.getOrderByIdAndRestaurantId(order.getId(), restaurantId)
                .orElseThrow(() -> new DomainException(DomainConstants.ORDER_NOT_FOUND));


        if (order.getStatus().equals(OrderStatusEnum.CANCELED.toString()) && orderUpdated.getStatus().equals(OrderStatusEnum.PENDING.toString())) {
            orderUpdated.setStatus(OrderStatusEnum.CANCELED.toString());
        } else if (order.getStatus().equals(OrderStatusEnum.IN_PREPARATION.toString()) && orderUpdated.getStatus().equals(OrderStatusEnum.PENDING.toString())) {
            orderUpdated.setChefId(userId);
            orderUpdated.setStatus(OrderStatusEnum.IN_PREPARATION.toString());
        } else if (order.getStatus().equals(OrderStatusEnum.READY.toString()) && orderUpdated.getStatus().equals(OrderStatusEnum.IN_PREPARATION.toString())) {
            orderUpdated.setStatus(OrderStatusEnum.READY.toString());
            String phone = userServicePort.getPhone(orderUpdated.getClientId());
            boolean isSendCode = messageServicePort.sendCode(phone);
            if (!isSendCode) {
                throw new DomainException(DomainConstants.MESSAGE_NOT_SENT);
            }
        } else if (order.getStatus().equals(OrderStatusEnum.DELIVERED.toString()) && orderUpdated.getStatus().equals(OrderStatusEnum.READY.toString())) {
            orderUpdated.setStatus(OrderStatusEnum.DELIVERED.toString());
            String phone = userServicePort.getPhone(orderUpdated.getClientId());
            boolean isVerifyCode = messageServicePort.verifyCode(phone, orderUpdated.getCode());
            if (!isVerifyCode) {
                throw new DomainException(DomainConstants.CODE_NOT_VERIFIED);
            }
        } else {
            throw new DomainException(DomainConstants.INVALID_ORDER_STATUS);
        }

        orderPersistencePort.updateOrder(orderUpdated);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderPersistencePort.deleteOrder(orderId);
    }

    @Override
    public Page<Order> getOrders(int page, int size, boolean ascending) {
        return null;
    }

    @Override
    public PaginatedModel<Order> getOrders(int pageNumber, int pageSize, String sortDirection, OrderStatusEnum status) {
        Long userId = userServicePort.getUserId();
        Employee employee = employeePersistencePort.getByEmployeeId(userId);
        if (employee == null || employee.getRestaurant() == null) {
            throw new DomainException(DomainConstants.RESTAURANT_NOT_FOUND);
        }

        Long restaurantId = employee.getRestaurant().getId();

        return orderPersistencePort.getOrders(pageNumber, pageSize, sortDirection, status, restaurantId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderPersistencePort.getOrderById(orderId);
        if (order == null) {
            throw new DomainException(DomainConstants.ORDER_NOT_FOUND);
        }

        if (!order.getClientId().equals(userServicePort.getUserId())) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }

        if (!order.getStatus().equals(OrderStatusEnum.PENDING.toString())) {
            throw new DomainException(DomainConstants.NOT_CANCELABLE_ORDER);
        }

        order.setStatus(OrderStatusEnum.CANCELED.toString());
        orderPersistencePort.updateOrder(order);
    }

}
