package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.OrderDishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IOrderDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {

    private final IOrderDishRepository orderdishRepository;

    private final OrderDishEntityMapper orderdishEntityMapper;

    @Override
    public void saveOrderDish(OrderDish orderdish) {
        orderdishRepository.save(orderdishEntityMapper.toEntity(orderdish));
    }

    @Override
    public List<OrderDish> getAllOrderDish() {
        List<OrderDishEntity> entityList = orderdishRepository.findAll();
        if (entityList.isEmpty()) {
            throw new IllegalArgumentException("No OrderDishs found");
        }
        return orderdishEntityMapper.toOrderDishList(entityList);
    }

    @Override
    public OrderDish getOrderDishById(Long orderdishId) {
        return orderdishEntityMapper.toOrderDish(orderdishRepository.findById(orderdishId)
                .orElseThrow(() -> new IllegalArgumentException("OrderDish not found")));
    }

    @Override
    public void updateOrderDish(OrderDish orderdish) {
        orderdishRepository.save(orderdishEntityMapper.toEntity(orderdish));
    }

    @Override
    public void deleteOrderDish(Long orderdishId) {
        orderdishRepository.deleteById(orderdishId);
    }

    @Override
    public Page<OrderDish> getOrderDishs(PageRequest pageRequest) {
        Page<OrderDishEntity> entityPage = orderdishRepository.findAll(pageRequest);
        if (entityPage.isEmpty()) {
            throw new IllegalArgumentException("No OrderDishs found");
        }
        return entityPage.map(orderdishEntityMapper::toOrderDish);
    }

    @Override
    public Page<OrderDish> getOrderDishs(int page, int size, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, ascending ? Sort.by("id").ascending() : Sort.by("id").descending());
        return orderdishRepository.findAll(pageable).map(orderdishEntityMapper::toOrderDish);
    }

    @Override
    public Page<OrderDish> getOrderDishs(int pageNumber, int pageSize, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection.equals("asc") ? Sort.by("id").ascending() : Sort.by("id").descending());
        return orderdishRepository.findAll(pageable).map(orderdishEntityMapper::toOrderDish);
    }

    @Override
    public Page<OrderDish> findAll(Pageable pageable) {
        return orderdishRepository.findAll(pageable).map(orderdishEntityMapper::toOrderDish);
    }
}
