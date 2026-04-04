package in.org.project.EcommercePlatform.controller;

import in.org.project.EcommercePlatform.dto.OrderResponseDto;
import in.org.project.EcommercePlatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/getall/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersOfCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(orderService.findAllOrdersOfCustomer(customerId));
    }
}
