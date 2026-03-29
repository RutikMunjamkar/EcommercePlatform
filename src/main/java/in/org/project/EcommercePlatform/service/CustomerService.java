package in.org.project.EcommercePlatform.service;

import in.org.project.EcommercePlatform.dto.OrderItemDto;
import in.org.project.EcommercePlatform.dto.OrderRequest;
import in.org.project.EcommercePlatform.dto.OrderResponseDto;
import in.org.project.EcommercePlatform.entity.Customer;
import in.org.project.EcommercePlatform.entity.Order;
import in.org.project.EcommercePlatform.entity.OrderItem;
import in.org.project.EcommercePlatform.entity.Product;
import in.org.project.EcommercePlatform.exception.CustomException;
import in.org.project.EcommercePlatform.repository.CustomerRepository;
import in.org.project.EcommercePlatform.repository.OrderItemRepository;
import in.org.project.EcommercePlatform.repository.OrderRepository;
import in.org.project.EcommercePlatform.repository.ProductRepository;
import in.org.project.EcommercePlatform.type.OrderItemStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    @Transactional
    public OrderResponseDto createOrder(Long customerId, OrderRequest orderRequest){
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomException("The Customer is not present",400);
        }
        Order order=Order.builder().customer(customer).paymentMethodType(orderRequest.getPaymentMethod()).build();
        orderRepository.save(order);
        Set<OrderItem>orderItemSet=new HashSet<>();
        Double totalPrice=0.0;
        Map<Long,Double>orderItemPriceMap=new HashMap<>();
        OrderResponseDto orderResponseDto=new OrderResponseDto();orderResponseDto.setOrderId(order.getId());
        for(OrderItemDto orderItemDto:orderRequest.getProducts()){
            Product product=productRepository.findById(orderItemDto.getProductId()).orElse(null);
            if(product==null){
                throw new CustomException("The Mentioned Product Is Not Preset",500);
            }
            if (product.getStock()<orderItemDto.getQuantity()){
                throw new CustomException(String.format("Insufficient stock for product %s. only Available %s",product.getId(),product.getStock()),400);
            }
            Double orderItemPrice=product.getPrice()*orderItemDto.getQuantity();
            totalPrice+=orderItemPrice;
            OrderItem orderItem=OrderItem.builder().quantityOrdered(orderItemDto.getQuantity()).orderItemStatus(OrderItemStatus.PENDING)
                    .product(product).order(order).orderItemTotalPrice(orderItemPrice).build();
            orderItemRepository.save(orderItem);
            orderItemSet.add(orderItem);
            orderItemPriceMap.put(orderItem.getId(),orderItemPrice);
        }
        order.setListOfOrderItem(orderItemSet);
        order.setOrderTotalAmount(totalPrice);
        orderResponseDto.setTotalPrice(totalPrice);
        orderResponseDto.setOrderItemPriceList(orderItemPriceMap);
        orderRepository.save(order);
        return orderResponseDto;
    }
}
