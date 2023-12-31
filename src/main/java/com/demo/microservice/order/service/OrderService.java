package com.demo.microservice.order.service;

import com.demo.microservice.order.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
//@Slf4j
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${spring.application.microservice-customer.url}")
    private String customerBaseUrl;
    private static final String CUSTOMER_ORDER_URL = "customerOrders/";
    public void createOrder(Order order) {
        final var url = customerBaseUrl + CUSTOMER_ORDER_URL + order.getCustomerId();
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("Order Request URL: {}", url);
        try {
            final var request = new HttpEntity<>(order, headers);
            final var responseEntity = restTemplate.postForEntity(url, request, Order.class);
            if (responseEntity.getStatusCode().isError()) {
                log.error("For Order id: {}, error response {} is received to create Order " +
                        "in Customer Microservice", order.getId(),
                        responseEntity.getStatusCode().getReasonPhrase());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("For Order UUID: %s, Customer Microservice Message: %s", order.getId(),
                                responseEntity.getStatusCodeValue()));
            }

            if (responseEntity.hasBody()) {
                log.error("Order from Response: {}", responseEntity.getBody().getId());
            }
        } catch (Exception e) {
            log.error("For Order ID: {} cannot create Order in Customer Microservice for reason: {}",
                    order.getId(), ExceptionUtils.getRootCauseMessage(e));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("\"For Order UUID: %s, Customer Microservice Response: %s", order.getId(), ExceptionUtils.getRootCauseMessage(e)));
        }
    }
}
