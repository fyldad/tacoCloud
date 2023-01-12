package com.example.test.repository;

import com.example.test.model.Taco;
import com.example.test.model.TacoOrder;
import com.example.test.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, String> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    List<TacoOrder> findAll(Pageable pageable);

}
