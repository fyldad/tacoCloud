package com.example.test;

import com.example.test.model.Ingredient;
import com.example.test.repository.reactive.ReactiveIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
public class ReactiveIngredientRepositoryTest {

    @Autowired
    ReactiveIngredientRepository repository;

    private final Ingredient flto = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
    private final Ingredient grbf = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
    private final Ingredient ched = new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE);

    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = repository.deleteAll()
                .thenMany(repository.saveAll(Flux.just(flto, grbf, ched)));
        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void saveAndFetch() {

        StepVerifier.create(repository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                            assertThat(ingredients).hasSize(3);
                            assertThat(ingredients).contains(flto);
                            assertThat(ingredients).contains(grbf);
                            assertThat(ingredients).contains(ched);
                        }
                ).verifyComplete();

        StepVerifier.create(repository.findById("FLTO"))
                .expectNext(flto)
                .verifyComplete();
    }

}
