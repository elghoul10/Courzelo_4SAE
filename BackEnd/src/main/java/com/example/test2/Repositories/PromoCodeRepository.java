package com.example.test2.Repositories;

import com.example.test2.Entities.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PromoCodeRepository   extends JpaRepository<PromoCode, Long> {

    @Query("SELECT b.user, SUM(p.prix) FROM Purshase p JOIN p.basket b GROUP BY p.basket.id")
    List<Object[]> findTotalPurchasesByBasket();
}
