package tj.alimov.gorcerystoreinventory.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.alimov.gorcerystoreinventory.model.Status;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestockOrderResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Status status;
    private Instant createdAt;
}

