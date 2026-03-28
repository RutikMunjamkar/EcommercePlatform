package in.org.project.EcommercePlatform.controller;

import in.org.project.EcommercePlatform.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantService merchantService;


    @PostMapping("/addproduct")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<Object> addProduct(@RequestBody Object productObject){
        return ResponseEntity.ok(merchantService.addProductsForMerchant(productObject));
    }


}
