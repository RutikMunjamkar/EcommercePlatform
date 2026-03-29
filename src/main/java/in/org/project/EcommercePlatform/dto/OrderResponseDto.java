package in.org.project.EcommercePlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private Double totalPrice;
    private Map<Long,Double>orderItemPriceList;
}