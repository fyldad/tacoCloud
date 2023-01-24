package com.example.test;

import com.example.test.model.Ingredient;
import com.example.test.model.Taco;
import com.example.test.model.TacoOrder;
import com.example.test.repository.reactive.ReactiveOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@DataMongoTest
public class ReactiveOrderRepositoryTest {

    @Autowired
    ReactiveOrderRepository repository;

    private final Ingredient flto = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
    private final Ingredient grbf = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
    private final Ingredient ched = new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE);

    @BeforeEach
    public void setup() {
        repository.deleteAll().subscribe();
    }

    @Test
    public void saveAndFetch() {
        TacoOrder order = createOrder();

        StepVerifier.create(repository.save(order))
                .expectNext(order)
                .verifyComplete();

        StepVerifier.create(repository.findById(order.getId()))
                .expectNext(order)
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .expectNext(order)
                .verifyComplete();
    }

    private TacoOrder createOrder() {
        TacoOrder order = new TacoOrder();
        order.setId(UUID.randomUUID().toString());
        order.setPlacedAt(new Date());

        order.setTacos(Arrays.asList(
                createTaco(Arrays.asList(flto, grbf)),
                createTaco(List.of(grbf)),
                createTaco(Arrays.asList(grbf, ched, flto))
        ));
        return order;
    }

    private Taco createTaco(List<Ingredient> ingredients) {
        Taco taco = new Taco();
        taco.setName(UUID.randomUUID().toString());
        taco.setIngredients(ingredients);
        return taco;
    }

}
