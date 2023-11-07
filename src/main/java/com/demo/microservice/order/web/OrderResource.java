package com.demo.microservice.order.web;

import com.demo.microservice.order.entity.Order;
import com.demo.microservice.order.repository.OrderRepository;
import com.demo.microservice.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
public class OrderResource {
    private final Logger log = LoggerFactory.getLogger(OrderResource.class);
    private static final String ENTITY_NAME = "order";
    @Value("${spring.application.name}")
    private String applicationName;

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderResource(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to save Order: {}", order);
        if (order.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The order already has an id");
        }
        final var result = orderRepository.save(order);
//        orderService.createOrder(result);

        HttpHeaders headers = new HttpHeaders();
        String message = String.format("A new %s is created with id %s", ENTITY_NAME, result.getId().toString());
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", result.getId().toString());
        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).headers(headers).body(result);
    }
}
