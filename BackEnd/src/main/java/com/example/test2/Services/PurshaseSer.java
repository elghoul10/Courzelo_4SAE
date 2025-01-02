package com.example.test2.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.test2.Repositories.PurshaseRep;
import com.example.test2.Entities.Purshase;

@Service
@AllArgsConstructor
public class PurshaseSer implements IPurshasSer{
    @Autowired
    private PurshaseRep purshaseRep;

    @Autowired
    private BasketSer basketSer;
    public Iterable<Purshase> getPurchases(Long id)
    { return purshaseRep.findByBasketId(id);}

    public void confirmPurchases(Long id)
    {
        Iterable<Purshase> purshases =  this.getPurchases(id);
        for (Purshase p :purshases
             ) {
            p.setStatus("Confirmed");
            purshaseRep.save(p);
        }
        basketSer.EmptyBasket(id);


    }


}
