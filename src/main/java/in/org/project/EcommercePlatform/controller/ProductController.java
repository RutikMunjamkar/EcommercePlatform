package in.org.project.EcommercePlatform.controller;

import in.org.project.EcommercePlatform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/allproducts")
    public ResponseEntity<Object> findAllProducts(){
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/productbyid/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable("id") Long productId){
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @DeleteMapping("/deleteById/{productId}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Long productId){
        productService.deleteById(productId);
        return ResponseEntity.ok("Product Is Deleted");
    }

    @PutMapping("update/{productId}")
    public ResponseEntity<Object> updateProductById(@PathVariable Long productId, @RequestBody Object updateRequestData){
        return ResponseEntity.ok(productService.updateProduct(productId,updateRequestData));
    }
}
