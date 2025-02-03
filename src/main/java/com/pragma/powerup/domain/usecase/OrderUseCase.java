package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IMessageServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Log;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserServicePort userServicePort;
    private final IEmployeePersistencePort employeePersistencePort;
    private final IMessageServicePort messageServicePort;
    private final ITraceabilityServicePort traceabilityServicePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public OrderUseCase(
            IOrderPersistencePort orderPersistencePort,
            IUserServicePort userServicePort,
            IEmployeePersistencePort employeePersistencePort,
            IMessageServicePort messageServicePort,
            ITraceabilityServicePort traceabilityServicePort, IRestaurantPersistencePort restaurantPersistencePort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.userServicePort = userServicePort;
        this.employeePersistencePort = employeePersistencePort;
        this.messageServicePort = messageServicePort;
        this.traceabilityServicePort = traceabilityServicePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
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

        Order orderCreated = orderPersistencePort.saveOrder(order);

        sendLog(orderCreated);

    }

    private void sendLog(Order orderCreated) {
        Log log = new Log();
        log.setClientId(orderCreated.getClientId());
        log.setOrderId(orderCreated.getId());
        log.setRestaurantId(orderCreated.getRestaurant().getId());
        log.setType(orderCreated.getStatus());
        log.setRestaurantId(orderCreated.getRestaurant().getId());
        log.setTimestamp(LocalDateTime.now());

        traceabilityServicePort.createLog(log);
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
        if (employee == null) {
            throw new DomainException(DomainConstants.EMPLOYEE_NOT_FOUND);
        }
        if (employee.getRestaurant() == null) {
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

        sendLog(orderUpdated);
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

    @Override
    public List<Log> getLogsByOrderId(Long orderId) {
        Order order = orderPersistencePort.getOrderById(orderId);
        if (order == null) {
            throw new DomainException(DomainConstants.ORDER_NOT_FOUND);
        }

        if (!order.getClientId().equals(userServicePort.getUserId())) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }

        return traceabilityServicePort.getLogsByOrderId(orderId);
    }

    @Override
    public Long getLogsTimeByOrderId(Long orderId) {

        Long userId = userServicePort.getUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantIdByOwnerId(userId);

        if (!restaurantPersistencePort.isOwnerOfRestaurant(userId, restaurant.getId())) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }

        return traceabilityServicePort.getLogsTimeByOrderId(orderId);
    }

    @Override
    public List<Log> getLogsTimeByRestaurant() {
        Long userId = userServicePort.getUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantIdByOwnerId(userId);

        if (!restaurantPersistencePort.isOwnerOfRestaurant(userId, restaurant.getId())) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }

        List<Long> orderIds = orderPersistencePort.getOrderIdsByRestaurantIdAndStatus(restaurant.getId(), OrderStatusEnum.DELIVERED);

        return traceabilityServicePort.getLogsTimeByOrders(orderIds);
    }

    @Override
    public List<Log> getAverageTimeByEmployee() {
        Long userId = userServicePort.getUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantIdByOwnerId(userId);

        if (!restaurantPersistencePort.isOwnerOfRestaurant(userId, restaurant.getId())) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }


        List<Long> employeeIds = employeePersistencePort.getEmployeeIdsByRestaurantId(restaurant.getId());
        List<Log> logs = new ArrayList<>();
        for (Long employeeId : employeeIds) {
            List<Long> orderIds = orderPersistencePort.getOrderIdsByChefIdAndStatus(employeeId, OrderStatusEnum.DELIVERED);
            List<Log> listLog = traceabilityServicePort.getLogsTimeByOrders(orderIds);
            long sum = listLog.stream().mapToLong(Log::getTime).sum();
            Long average = sum / listLog.size();
            Log log = new Log();
            log.setChefId(employeeId);
            log.setTime(average);
            logs.add(log);
        }

        return logs;
    }

}
