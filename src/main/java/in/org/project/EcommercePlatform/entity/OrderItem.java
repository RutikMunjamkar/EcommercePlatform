package in.org.project.EcommercePlatform.entity;

import in.org.project.EcommercePlatform.type.OrderItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="ORDER_ITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double quantityOrdered;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
