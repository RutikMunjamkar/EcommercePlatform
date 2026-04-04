package in.org.project.EcommercePlatform.service;

import in.org.project.EcommercePlatform.dto.OrderItemDto;
import in.org.project.EcommercePlatform.dto.OrderResponseDto;
import in.org.project.EcommercePlatform.entity.Customer;
import in.org.project.EcommercePlatform.entity.Order;
import in.org.project.EcommercePlatform.entity.OrderItem;
import in.org.project.EcommercePlatform.exception.CustomException;
import in.org.project.EcommercePlatform.repository.CustomerRepository;
import in.org.project.EcommercePlatform.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public List<OrderResponseDto> findAllOrdersOfCustomer(Long customerId){
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomException(String.format("No customer %s id found",customerId),400);
        }
        List<OrderResponseDto>orderResponseDtoArrayList=new ArrayList<>();
        for(Order order:customer.getListOfOrders()){
            List<OrderItemDto>orderItemDtoList=new ArrayList<>();
            for(OrderItem orderItem:order.getListOfOrderItem()){
                OrderItemDto orderItemDto=OrderItemDto.builder().build();
                orderItemDto.setPrice(orderItem.getOrderItemTotalPrice());
                orderItemDto.setQuantity(orderItem.getQuantityOrdered());
                orderItemDto.setProductId(orderItem.getProduct().getId());
                orderItemDtoList.add(orderItemDto);
            }
            OrderResponseDto orderResponseDto=OrderResponseDto.builder().orderId(order.getId())
                    .totalPrice(order.getOrderTotalAmount())
                    .orderItemDtoList(orderItemDtoList).build();
            orderResponseDtoArrayList.add(orderResponseDto);
        }
        return orderResponseDtoArrayList;
    }
}
