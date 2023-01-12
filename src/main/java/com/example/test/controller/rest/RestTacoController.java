package com.example.test.controller.rest;

import com.example.test.model.Taco;
import com.example.test.model.TacoOrder;
import com.example.test.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tacos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8443")
public class RestTacoController {

    private final OrderRepository orderRepository;

    @GetMapping(params = "recent")
    public List<Taco> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("placedAt").descending());
        return orderRepository.findAll(pageRequest).stream()
                .map(TacoOrder::getTacos)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable String id) {
        return orderRepository.findById(id).stream()
                .map(TacoOrder::getTacos)
                .flatMap(Collection::stream)
                .findFirst()
                .map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
