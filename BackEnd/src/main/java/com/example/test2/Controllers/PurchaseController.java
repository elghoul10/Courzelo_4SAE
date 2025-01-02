package com.example.test2.Controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.test2.Services.PurshaseSer;
import com.example.test2.Entities.Purshase;

@Controller
@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("pi/purchase")
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseController {

    @Autowired
    PurshaseSer purshaseSer;


    @PostMapping("confirmPurchases")
    public void confirmPurchases(@RequestParam("idb") Long id){
        purshaseSer.confirmPurchases(id);
    }


    @GetMapping("getPurchases")
    public Iterable<Purshase> getPurchases(@RequestParam("idb") Long id){
        return purshaseSer.getPurchases(id);

    }

}
