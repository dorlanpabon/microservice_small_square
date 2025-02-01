package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private String status;
    private Restaurant restaurant;
    private Long chefId;
    private List<OrderDish> orderDishes;
    private String code;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", restaurant=" + restaurant +
                ", chefId=" + chefId +
                ", orderDishes=" + orderDishes +
                ", code='" + code + '\'' +
                '}';
    }
}
