package tj.alimov.gorcerystoreinventory.dto.stockBatch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.alimov.gorcerystoreinventory.model.Product;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchRequestDto {
    @NotBlank(message = "Product id required")
    private Long productId;
    @NotBlank(message = "Quantity required")
    @Min(value = 1, message = "Min value for quantity is 1")
    private Integer quantity;
    private Instant expiryDate;
    @NotBlank(message = "Received date required")
    private Instant receivedDate;
}
