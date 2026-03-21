package in.org.project.EcommercePlatform.entity;

import in.org.project.EcommercePlatform.type.MerchantStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="MERCHANT",uniqueConstraints = {@UniqueConstraint(name = "gst_pan_number_unique",columnNames = {"gstNumber", "panNumber"})})
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String companyName;

    @Pattern(regexp = "^[A-Z0-9]+$", message = "GstNumber should be AlphaNumeric")
    private String gstNumber;

    @Pattern(regexp="^[A-Z0-9]+$", message="PanNumber must be Alphanumeric")
    private String panNumber;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "merchant", orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Product> listOfProducts=new HashSet<>();

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BankDetails> listOfBankDetails;

    @OneToOne
    @MapsId
    private User user;
}