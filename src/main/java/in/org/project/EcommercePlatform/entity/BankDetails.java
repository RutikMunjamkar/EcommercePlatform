package in.org.project.EcommercePlatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BANK_DETAILS",uniqueConstraints = {@UniqueConstraint(name = "unique_account_ifsc_bankName",
                    columnNames = {"accountNumber", "ifscCode", "bankName"})})
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name="IFSC_CODE")
    private String ifscCode;

    private String bankName;

    @ManyToOne
    @JoinColumn(name="merchant_id")
    private Merchant merchant;
}
