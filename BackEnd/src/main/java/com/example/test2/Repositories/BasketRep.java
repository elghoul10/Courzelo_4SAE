package com.example.test2.Repositories;

import com.example.test2.Entities.Basket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface BasketRep extends CrudRepository<Basket,Long> {

    Basket findByUserUserId(Long idU);
    @Query("SELECT b FROM Basket b JOIN b.user u WHERE u.email = :email OR DATE(b.date_add) = :dateAdd OR b.promoCode = :promoCode")
    List<Basket> findByEmailOrDateAddOrPromoCode(@Param("email") String email, @Param("dateAdd") Date dateAdd, @Param("promoCode") String promoCode);

}
