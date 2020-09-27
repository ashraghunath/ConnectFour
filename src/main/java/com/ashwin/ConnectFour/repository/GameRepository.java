package com.ashwin.ConnectFour.repository;


import com.ashwin.ConnectFour.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game,Long> {

    @Override
    Iterable<Game> findAllById(Iterable<Long> iterable);

    @Override
    Optional<Game> findById(Long id);

    @Override
    Iterable<Game> findAll();

}
