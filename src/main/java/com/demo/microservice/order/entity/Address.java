package com.demo.microservice.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @NotNull
    @Field(name = "street_name")
    private String streetName;
    @NotNull
    private String streetNumber;
    private String additionalInfo;
    @NotNull
    private String zipCode;
    private String state;
    @NotNull
    private String country;
}
