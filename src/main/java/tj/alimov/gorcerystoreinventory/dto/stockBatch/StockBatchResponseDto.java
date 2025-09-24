package tj.alimov.gorcerystoreinventory.dto.stockBatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.model.Product;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchResponseDto {
    private Long id;
    private ProductResponseDto product;

    private Integer quantity;

    private Instant expiryDate;
    private Instant receivedDate;
}
