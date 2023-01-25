package com.example.test.repository.reactive;

import com.example.test.model.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReactiveIngredientRepository extends ReactiveCrudRepository<Ingredient, String> {

    Flux<Ingredient> findAllByType(Ingredient.Type type);

}
