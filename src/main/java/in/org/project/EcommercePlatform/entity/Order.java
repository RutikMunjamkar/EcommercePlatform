package in.org.project.EcommercePlatform.entity;

import in.org.project.EcommercePlatform.type.PaymentMethodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDER_DETAILS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentMethodType paymentMethodType;

    private Double orderTotalAmount;

    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<OrderItem> listOfOrderItem=new HashSet<>();
}