package com.example.test.repository.reactive;

import com.example.test.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveUserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findUserByUsername(String username);
}
