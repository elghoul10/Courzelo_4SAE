package com.example.test2.Controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.test2.Services.PromoCodeService;
import com.example.test2.Entities.PromoCode;

import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("pi/promocode")
@CrossOrigin(origins = "http://localhost:4200")

public class PromoCodeController {

    @Autowired
    PromoCodeService promoCodeService;

    @PostMapping("add")
    public PromoCode add(@RequestBody PromoCode pc){
        return promoCodeService.AddPromoCode(pc);
    }

    @PostMapping("Edit")
    public PromoCode edit(@RequestBody PromoCode pc){
        return promoCodeService.EditPromoCode(pc);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam("idpc") Long idpc){
        promoCodeService.DeletePromoCode(idpc);
    }

    @GetMapping("getAll")
    public List<PromoCode> FindAll() {
        return promoCodeService.FindAll();
    }



    @GetMapping("getlist")
    public List<String> FindList() {
            return promoCodeService.listUsersForPromoCode();
            }

}