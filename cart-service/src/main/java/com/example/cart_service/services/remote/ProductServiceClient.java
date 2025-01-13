package com.example.cart_service.services.remote;

import com.example.cart_service.response.ApiProductResponse;
import com.example.cart_service.response.ApiResponse;
import com.example.cart_service.response.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProductServiceClient {

    private final WebClient webClient;
    @Autowired
    private ModelMapper modelMapper;
    public ProductServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://product-service").build();
    }

    public ProductResponse getProductById(Long productId) {
        ApiProductResponse apiProductResponse = this.webClient
                .get()
                .uri("/products/{productId}",productId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError, // Handle 4xx errors
                        response -> Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError, // Handle 5xx errors
                        response -> Mono.error(new RuntimeException("Server error: " + response.statusCode()))
                )
                .bodyToMono(ApiProductResponse.class)
                .block();

            return apiProductResponse.getData();
//        System.out.println("Raw Response: " + apiResponse.getData());
//        // Convert raw JSON to ProductResponse
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(apiResponse.getData(), ProductResponse.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to map response", e);
//        }
        }
}
