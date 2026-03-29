package in.org.project.EcommercePlatform.entity;

import in.org.project.EcommercePlatform.type.OrderItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="ORDER_ITEM")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long quantityOrdered;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double orderItemTotalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}