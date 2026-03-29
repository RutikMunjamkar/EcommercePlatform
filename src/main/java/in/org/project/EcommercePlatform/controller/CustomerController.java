package in.org.project.EcommercePlatform.controller;

import in.org.project.EcommercePlatform.dto.OrderRequest;
import in.org.project.EcommercePlatform.dto.OrderResponseDto;
import in.org.project.EcommercePlatform.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/addToCart")
    public ResponseEntity<JsonNode> addToCart(){
        return null;
    }

    @PostMapping("/order")
    @PreAuthorize("#customerId==authentication.principal.id")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestParam("customerId") Long customerId, @RequestBody @Valid OrderRequest orderRequest){
        return ResponseEntity.ok(customerService.createOrder(customerId,orderRequest));
    }
}
