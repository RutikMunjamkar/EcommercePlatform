package in.org.project.EcommercePlatform.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private Double price;

    private Double rating;

    @ManyToOne
    @JoinColumn(name="merchant_id")
    private Merchant merchant;

    @OneToMany(mappedBy = "product",orphanRemoval = true, cascade = CascadeType.ALL)
    Set<OrderItem> listOfOrderItem=new HashSet<>();
}
