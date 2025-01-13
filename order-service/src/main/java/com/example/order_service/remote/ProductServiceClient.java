package com.example.order_service.remote;


import com.example.order_service.response.ApiProductResponse;
import com.example.order_service.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                        response -> Mono.error(new RuntimeException("Server error: " + response.statusCode() + response.bodyToMono(String.class)))
                )
                .bodyToMono(ApiProductResponse.class)
                .block();

            return apiProductResponse.getData();
        }

    public void saveProduct(Long productId,ProductResponse updatedProduct) {
                 this.webClient
                .put()
                .uri("/products/{productId}/update",productId)
                .bodyValue(updatedProduct)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError, // Handle 4xx errors
                        response -> Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError, // Handle 5xx errors
                        response -> Mono.error(new RuntimeException("Server error: " + response.statusCode()))
                )
                .bodyToMono(String.class)
                .block();
    }
}
