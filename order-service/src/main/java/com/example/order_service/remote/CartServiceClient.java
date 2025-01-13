package com.example.order_service.remote;

import com.example.order_service.response.ApiCartResponse;
import com.example.order_service.response.ApiProductResponse;
import com.example.order_service.response.ApiResponse;
import com.example.order_service.response.CartResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CartServiceClient {
    private final WebClient webClient;

    public CartServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://cart-service").build();
    }

    public CartResponse getCart(Long cartId, Long userId) {
        ApiCartResponse apiCartResponse = this.webClient
                .get()
                .uri(uriBuilder ->
                    uriBuilder.path("/carts/{cartId}/my-cart")
                            .queryParam("userId", userId)
                            .build(cartId)
                ).retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError, // Handle 4xx errors
                        response -> Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError, // Handle 5xx errors
                        response -> Mono.error(new RuntimeException("Server error: " + response.statusCode()))
                )
                .bodyToMono(ApiCartResponse.class)
                .block();

        return  apiCartResponse.getData();
    }

    public void clearCart(Long cartId) {
        this.webClient
                .delete()
                .uri("/carts/{cartId}/clear",cartId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError, // Handle 4xx errors
                        response -> Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError, // Handle 5xx errors
                        response -> Mono.error(new RuntimeException("Server error: " + response.statusCode()))
                )
                .bodyToMono(ApiCartResponse.class)
                .block();

    }

}
