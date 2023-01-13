package com.example.test.controller.thymeleaf;

import com.example.test.config.OrderConfig;
import com.example.test.integration.UpperCaseGateway;
import com.example.test.integration.file.FileWriterGateway;
import com.example.test.messaging.jms.JmsOrderSender;
import com.example.test.messaging.kafka.KafkaOrderSender;
import com.example.test.messaging.rabbit.RabbitOrderSender;
import com.example.test.model.TacoOrder;
import com.example.test.model.User;
import com.example.test.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderConfig orderConfig;
    private final JmsOrderSender jmsOrderSender;
    private final RabbitOrderSender rabbitOrderSender;
    private final KafkaOrderSender kafkaOrderSender;
    private final FileWriterGateway fileWriterGateway;
    private final UpperCaseGateway upperCaseGateway;

    @GetMapping("current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus status, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) return orderForm();
        order.setUser(user);
        order.setPlacedAt(new Date());

        orderRepository.save(order);
        jmsOrderSender.send(order);
        rabbitOrderSender.send(order);
        kafkaOrderSender.send(order);
        fileWriterGateway.writeToFile("orders.log", order.toString());

        log.info("order submitted: {}", upperCaseGateway.upperCase(order.toString()));
        status.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, orderConfig.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

}
