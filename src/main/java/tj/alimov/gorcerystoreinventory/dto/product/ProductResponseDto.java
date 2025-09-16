package tj.alimov.gorcerystoreinventory.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.alimov.gorcerystoreinventory.dto.category.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierResponseDto;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private CategoryResponseDto category;
    private SupplierResponseDto supplier;
    private Integer minStockLevel;
    private Instant createdAt;
    private Instant updatedAt;
}
