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

    @Autowired
    KafkaProducerService kafkaProducerService;


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
        orderResponseDto.setPaymentType(orderRequest.getPaymentMethod().toString());
        //address to be added
        for(OrderItemDto orderItemDto:orderRequest.getProducts()){
            Product product=productRepository.findById(orderItemDto.getProductId()).orElse(null);
            if(product==null){
                throw new CustomException("The Mentioned Product Is Not Preset",500);
            }
            if (product.getStock()<orderItemDto.getQuantity()){
                throw new CustomException(String.format("Insufficient stock for product %s. only Available %s",product.getId(),product.getStock()),400);
            }
            product.setStock(product.getStock()-orderItemDto.getQuantity());
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
        kafkaProducerService.produceOrderMessage(objectMapper.writeValueAsString(orderResponseDto));
        return orderResponseDto;
    }

    @Transactional
    public String cancelOrder(Long orderId, Long customerId){
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomException(String.format("No customer %s id found",customerId),400);
        }
        Order order=orderRepository.findById(orderId).orElse(null);
        if(order==null){
            throw new CustomException(String.format("No order id %s for customer %s found",orderId,customerId),400);
        }
        for(OrderItem orderItem:order.getListOfOrderItem()){
            Product product=productRepository.findById(orderItem.getId()).orElse(null);
            product.setStock(product.getStock()+orderItem.getQuantityOrdered());
        }
        orderRepository.deleteById(order.getId());
        return "Order SuccessFully Deleted";
    }
}
