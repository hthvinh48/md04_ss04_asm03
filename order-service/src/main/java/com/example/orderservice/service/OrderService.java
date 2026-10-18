package com.example.orderservice.service;

import com.example.orderservice.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final String PRODUCT_SERVICE = "product-service";

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient = RestClient.create();

    public ServiceInstance findProductServiceInstance() {
        List<ServiceInstance> instances = discoveryClient.getInstances(PRODUCT_SERVICE);

        if (instances.isEmpty()) {
            throw new ServiceUnavailableException("No instances of product-service found");
        }

        return instances.get(0);
    }

    public String getProductServices() {
        ServiceInstance instance = findProductServiceInstance();
        String url = instance.getUri() + "/api/products";

        try {
            return restClient.get().uri(url)
                    .retrieve()
                    .body(String.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("Failed to call product-service: " + e.getMessage());
        }
    }
}
