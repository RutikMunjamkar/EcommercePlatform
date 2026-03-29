package in.org.project.EcommercePlatform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT",uniqueConstraints = {@UniqueConstraint(name = "unique_product_user_merchatn",columnNames = {"productName","merchant_id"})})
public class  Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private Double price;

    private Double rating;

    @ManyToOne
    @JoinColumn(name="merchant_id")
    @JsonBackReference
    private Merchant merchant;

    @OneToMany(mappedBy = "product",orphanRemoval = true, cascade = CascadeType.ALL)
    Set<OrderItem> listOfOrderItem=new HashSet<>();
}