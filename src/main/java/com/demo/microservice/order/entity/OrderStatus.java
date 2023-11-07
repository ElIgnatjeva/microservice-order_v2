package com.demo.microservice.order.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OrderStatus {
    CREATED("CREATED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    DELETED("DELETED"),
    RETURNING("RETURNING"),
    RETURNED("RETURNED");
    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
