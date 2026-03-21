package in.org.project.EcommercePlatform.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDER_DETAILS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<OrderItem> listOfOrderItem=new HashSet<>();
}