package in.org.project.EcommercePlatform.service;

import in.org.project.EcommercePlatform.entity.Merchant;
import in.org.project.EcommercePlatform.entity.Product;
import in.org.project.EcommercePlatform.entity.User;
import in.org.project.EcommercePlatform.exception.CustomException;
import in.org.project.EcommercePlatform.repository.MerchantRepository;
import in.org.project.EcommercePlatform.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Service
public class MerchantService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Map<String,Long> addProductsForMerchant(Object productData){
        try{
            JsonNode jsonNode =objectMapper.valueToTree(productData);
            User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Merchant merchant=merchantRepository.findById(user.getId()).orElseThrow(()->new CustomException("No Merchant Found",500));
            Map<String,Long> listOfProductIds=new HashMap<>();
            for(JsonNode json:jsonNode){
                if(!json.has("productName") || !json.has("stock") || !json.has("price")){
                    throw new CustomException("The Json is not valid",500);
                }
                Product product=Product.builder().productName(json.get("productName").asString()).stock(json.get("stock").asLong())
                        .price(json.get("stock").asDouble()).merchant(merchant).build();
                productRepository.save(product);
                listOfProductIds.put(json.get("productName").asString(), product.getId());
            }
            return listOfProductIds;
        }
        catch (Exception e){
            throw new CustomException(e.getMessage(),500);
        }
    }
}
