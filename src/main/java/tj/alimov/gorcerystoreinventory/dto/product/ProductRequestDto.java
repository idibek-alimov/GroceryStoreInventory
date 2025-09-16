package tj.alimov.gorcerystoreinventory.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Category id is required")
    Long categoryId;
    @NotBlank(message = "Supplier id is required")
    Long supplierId;
    @Min(value = 1, message = "Min stock level should be > 0")
    @NotNull(message = "Min stock level is required")
    Integer minStockLevel;
}
