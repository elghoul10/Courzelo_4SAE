package com.example.test2.Services;

import com.example.test2.Entities.Purshase;

public interface IPurshasSer {
    Iterable<Purshase> getPurchases(Long id);
    void confirmPurchases(Long id);

}
