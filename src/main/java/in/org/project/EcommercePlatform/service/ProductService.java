package in.org.project.EcommercePlatform.service;

import in.org.project.EcommercePlatform.entity.Product;
import in.org.project.EcommercePlatform.exception.CustomException;
import in.org.project.EcommercePlatform.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Transactional
    public Object findAllProducts(){
        List<Product> listOfProducts=productRepository.findAll();
        return objectMapper.convertValue(listOfProducts,Object.class);
    }

    @Transactional
    public Object findProductById(Long id){
        Product product=productRepository.findById(id).orElseThrow(()->new CustomException("No Product for this Id found",500));
        return objectMapper.valueToTree(product);
    }

    @Transactional
    public void deleteById(Long productId){
        productRepository.deleteById(productId);
    }

    @Transactional
    public Product updateProduct(Long productId,Object updateRequestData){
        Product product=productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new CustomException("There is not product"+productId+"preset",500);
        }
        Map<String,Object> productMap=objectMapper.convertValue(product, new TypeReference<>() {});
        Map<String,Object>requestDataMap=objectMapper.convertValue(updateRequestData, new TypeReference<>() {});
        for(Map.Entry<String,Object>e:requestDataMap.entrySet()){
            String requestKey=e.getKey();
            if(!productMap.containsKey(requestKey)){
                throw new CustomException(requestKey +": is not valid",500);
            }
            productMap.put(requestKey,e.getValue());
        }
        Product newProduct=objectMapper.convertValue(productMap,Product.class);
        productRepository.save(newProduct);
        return newProduct;
    }
}
