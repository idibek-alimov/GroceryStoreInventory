package tj.alimov.gorcerystoreinventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestockOrderRequestDto {
    @NotBlank(message = "ProductId required")
    private Long productId;
    @NotBlank(message = "Quantity required")
    @Min(1)
    private Integer quantity;
}
