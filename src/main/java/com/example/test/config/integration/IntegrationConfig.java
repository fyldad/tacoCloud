package com.example.test.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class IntegrationConfig {

    private final String dir = "C:\\Users\\WebUser\\Documents\\tempFiles";

    @Bean
    public IntegrationFlow fileIntegrationFlow() {
        return IntegrationFlow
                .from(MessageChannels.direct("textInChannel"))
                .<String>filter(s -> s.length() > 50)
                .<String, String>transform(String::toUpperCase)
                .handle(Files.outboundAdapter(new File(dir))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }

    @Bean
    public IntegrationFlow StringIntegrationFlow() {
        return IntegrationFlow
                .from("inChannel")
                .<String, String>transform(String::toUpperCase)
                .channel("outChannel")
                .get();
    }

    @Bean
    public IntegrationFlow printer() {
        return IntegrationFlow
                .from("printerChannel")
                .handle((payload, headers) -> {
                    System.out.println(payload);
                    System.out.println(headers);
                    return null;
                })
                .get();
    }

    @Bean
    @InboundChannelAdapter(poller = @Poller(fixedRate = "10000"), channel = "printerChannel")
    public MessageSource<Integer> numberSource(AtomicInteger source) {
        return new AbstractMessageSource<>() {
            @Override
            protected Object doReceive() {
                return source.getAndIncrement();
            }

            @Override
            public String getComponentType() {
                return Integer.class.getTypeName();
            }
        };
    }

    @Bean
    public IntegrationFlow fileReadingFlow() {
        return IntegrationFlow
                .from(Files.inboundAdapter(new File(dir)), c -> c.poller(Pollers.fixedRate(1000)))
                .channel("printerChannel")
                .get();
    }

    @Bean
    public AtomicInteger atomicInteger() {
        return new AtomicInteger();
    }

}
