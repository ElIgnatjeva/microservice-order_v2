package com.demo.microservice.order.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotBlank
    @Field(name = "customer_id")
    private String customerId;
    @Field(name = "created_at")
    @CreatedDate
    private Instant createdAt;
    @Field(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
    @Version
    private Integer version;
    @Field(name = "status")
    private OrderStatus status = OrderStatus.CREATED;
    @Field(name = "payment_status")
    private Boolean paymentStatus = Boolean.FALSE;
    @NotNull
    @Field(name = "payment_method")
    private PaymentType paymentMethod;
    @NotNull
    @Field(name = "payment_details")
    private String paymentDetails;
    @Field(name = "shipping_address")
    private Address shippingAddress;

    @NotEmpty
    @Field(name = "products")
    private Set<@Valid Product> products;
}
