package com.example.test2.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.test2.Repositories.PromoCodeRepository;
import com.example.test2.Entities.PromoCode;
import com.example.test2.Entities.User;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PromoCodeService implements IPromoCodeService{
@Autowired
    PromoCodeRepository promoCodeRepository;

    @Override
    public PromoCode AddPromoCode(PromoCode pc) {
        return promoCodeRepository.save(pc);
    }

    @Override
    public PromoCode EditPromoCode(PromoCode pc) {
        return promoCodeRepository.save(pc);
    }

    @Override
    public void DeletePromoCode(Long idpc) {
        PromoCode pc = promoCodeRepository.findById(idpc).get();
         promoCodeRepository.delete(pc);

    }

    public List<PromoCode> FindAll() {
        return promoCodeRepository.findAll();

    }

    public List<String> listUsersForPromoCode(){
        List<Object[]>usersWithTotal = promoCodeRepository.findTotalPurchasesByBasket();

        List<String> emails = new ArrayList<>();

        for (Object[] userWithTotal : usersWithTotal) {
            // The user object is at index 0 in the array
            Object userObject = userWithTotal[0];

            if (userObject instanceof User) {
                User user = (User) userObject;
                String email = user.getEmail();
                emails.add(email);
            }
        }

        return emails;
    }
}
