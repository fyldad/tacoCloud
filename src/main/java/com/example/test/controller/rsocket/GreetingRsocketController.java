package com.example.test.controller.rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class GreetingRsocketController {

    @MessageMapping("greeting")
    public Mono<String> handleGreeting(Mono<String> greetingMono) {
        return greetingMono.doOnNext(greeting -> log.info("get a greeting: {}", greeting))
                .map(greeting -> "response");
    }

}
