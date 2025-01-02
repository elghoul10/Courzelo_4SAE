package com.example.test2.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.test2.Repositories.*;
import com.example.test2.Entities.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BasketSer implements IBasketSer {
    @Autowired
    private BasketRep BasketR;
    @Autowired
    private PurshaseRep purshaseRep;
    @Autowired
    private CousreRepository CourseRep;

    @Autowired
    private UserRepository userRep;
    @Autowired
    private PromoCodeRepository promoCodeRepository;

    public Basket addToBasket(Long idcourse, Long idu) {
        Basket b = BasketR.findByUserUserId(idu);
        Course course = CourseRep.findById(idcourse).get();
        User user = userRep.findById(idu).get();
        if (b == null){
            Basket basket = Basket.builder().date_add(new Date()).Total((double) course.getPrix()).user(user).build();
            Purshase purshase = Purshase.builder().date_purchase(new Date()).course(course).Status("en cours").basket(basket).prix(course.getPrix()).build();
            purshaseRep.save(purshase);
            BasketR.save(basket);

        }else{
            for (Purshase p :b.getPurchases()
                 ) {
                if(p.getCourse() == course){
                    return b;
                }
            }
            Purshase purshase = Purshase.builder().date_purchase(new Date()).course(course).Status("en cours").basket(b).prix(course.getPrix()).build();
            purshaseRep.save(purshase);
            b.getPurchases().add(purshase);
            b.setTotal(b.getTotal()+purshase.getPrix());
            b.setDate_add(new Date());
            BasketR.save(b);

        }

        return b;

    }

    public Iterable<Basket> showAll()
    { return BasketR.findAll();}

    public Iterable<Basket> SerachResult(String key,String date)
    {
        Date ts = java.sql.Date.valueOf(date);

        return BasketR.findByEmailOrDateAddOrPromoCode(key,ts,key);}

    public Optional<Basket> findOne(Long id)
    { return BasketR.findById(id);}



    public Basket showBasket(Long idU)
    { Basket b = BasketR.findByUserUserId(idU);
    Iterable<Purshase> purshases = b.getPurchases();
        for (Purshase p: purshases
             ) {
            if (p.getStatus().equals("Confirmed")){
                b.getPurchases().remove(p);
            }

        }
        return b;
    }

    public Basket dropItem(Long idb, Long idp){
        Basket b = BasketR.findById(idb).get();
        Purshase purshase = purshaseRep.findById(idp).get();
        for (Purshase p: b.getPurchases()
             ) {
            if(p == purshase){
                b.setTotal(b.getTotal() - purshase.getPrix());
                BasketR.save(b);
                purshaseRep.delete(purshase);
            }
        }
        return b;
    }

    public String EmptyBasket(Long idb){
        Basket b = BasketR.findById(idb).get();

        for (Purshase p: b.getPurchases()
             ) {
            if(p.getStatus().equals("en cours")) {
                purshaseRep.delete(p);

            }

        }
        b.setTotal((double) 0);
        BasketR.save(b);
        return "{\"msg\":\"affected\"}";

    }

    public String ApplyCode(String code, Long idb){
        Basket b = BasketR.findById(idb).get();
        Set<PromoCode> coupons = (Set<PromoCode>) promoCodeRepository.findAll();
        boolean isEnumValue = false;
        int discount = 0;

        for (PromoCode c : coupons) {
            if (c.getPromoCode().equals(code)) {
                isEnumValue = true;
                discount = c.getDiscount();
                break;
            }
        }
        if (isEnumValue){
            b.setTotal(b.getTotal()- (b.getTotal() * discount / 100));
            BasketR.save(b);
            return "{\"msg\":\"applyed\"}";
        }else {
            return "{\"msg\":\"coupon invalide\"}";
        }
    }
}
