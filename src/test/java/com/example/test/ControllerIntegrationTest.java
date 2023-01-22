package com.example.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getOrders() {
        webTestClient
//                .get().uri("/api/func/orders")
                .get().uri("/api/reactOrders")
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

}
