package com.example.test2.Services;

import com.example.test2.Entities.Basket;

import java.util.Optional;

public interface IBasketSer {
    Basket addToBasket(Long idcourse, Long idu);
    Iterable<Basket> SerachResult(String key, String  date);

    Iterable<Basket> showAll();

     Optional<Basket> findOne(Long id);
     Basket dropItem(Long idb, Long idC);

     String EmptyBasket( Long idb);




     String ApplyCode(String code, Long idb);


    }
