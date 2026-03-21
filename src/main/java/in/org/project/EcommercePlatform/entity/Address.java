package in.org.project.EcommercePlatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER_ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String verifiedAddressDetails;

    @Column(nullable = false)
    private String phoneNo;

    @Column(length = 6)
    private Long zipCode;

    @Column(nullable = false)
    private String addressStats;

    @OneToOne
    @MapsId
    private User user;
}
