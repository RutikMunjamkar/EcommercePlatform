package in.org.project.EcommercePlatform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    @NotNull
    private Long productId;

    @NotNull
    private Long quantity;
}
