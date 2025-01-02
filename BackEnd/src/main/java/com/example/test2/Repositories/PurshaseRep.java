package com.example.test2.Repositories;

import com.example.test2.Entities.Purshase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurshaseRep extends CrudRepository<Purshase,Long> {
    Iterable<Purshase> findByBasketId(Long id);
}
