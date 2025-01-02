package com.example.test2.Services;

import com.example.test2.Entities.PromoCode;

import java.util.List;

public interface IPromoCodeService {

    PromoCode AddPromoCode (PromoCode pc);
    PromoCode EditPromoCode (PromoCode pc);

    void DeletePromoCode(Long idpc);


     List<PromoCode> FindAll() ;

}
