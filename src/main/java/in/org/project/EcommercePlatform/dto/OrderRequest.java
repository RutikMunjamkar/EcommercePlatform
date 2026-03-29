package in.org.project.EcommercePlatform.dto;

import in.org.project.EcommercePlatform.type.PaymentMethodType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull
    private Set<OrderItemDto> products;
    @NotNull
    private PaymentMethodType paymentMethod;
    @NotNull
    private String address;
}
