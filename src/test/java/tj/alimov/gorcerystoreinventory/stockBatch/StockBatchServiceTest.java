package tj.alimov.gorcerystoreinventory.stockBatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchRequestDto;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchResponseDto;
import tj.alimov.gorcerystoreinventory.mapper.StockBatchMapper;
import tj.alimov.gorcerystoreinventory.model.Product;
import tj.alimov.gorcerystoreinventory.model.StockBatch;
import tj.alimov.gorcerystoreinventory.repository.ProductRepository;
import tj.alimov.gorcerystoreinventory.repository.StockBatchRepository;
import tj.alimov.gorcerystoreinventory.service.StockBatchService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.util.Optional;


public class StockBatchServiceTest {
    @Mock
    private StockBatchRepository stockBatchRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockBatchMapper stockBatchMapper;

    @InjectMocks
    private StockBatchService stockBatchService;

    private StockBatch stockBatch;
    private StockBatchRequestDto requestDto;
    private StockBatchResponseDto responseDto;

    private Product product;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id(1L)
                .name("product")
                .minStockLevel(2)
                .build();

        productResponseDto = new ProductResponseDto(1L, "product", null, null, 1, null, null);

        stockBatch = StockBatch.builder()
                .id(1L)
                .product(product)
                .quantity(1)
                .build();

        requestDto = new StockBatchRequestDto(product.getId(), stockBatch.getQuantity(), Instant.now(), Instant.now());
        responseDto = new StockBatchResponseDto(1l, productResponseDto, 1, null, null);
    }

    @Test
    void create_ShouldReturnStockBatchResponseDto(){
        when(stockBatchRepository.save(any(StockBatch.class))).thenReturn(stockBatch);
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(stockBatchMapper.toDto(any(StockBatch.class))).thenReturn(responseDto);
        when(stockBatchMapper.toEntity(any(StockBatchRequestDto.class))).thenReturn(stockBatch);

        StockBatchResponseDto response = stockBatchService.create(requestDto);

        assertNotNull(response);
        assertEquals(response.getId(), responseDto.getId());
        assertEquals(response.getProduct(), responseDto.getProduct());
        assertEquals(response.getQuantity(), responseDto.getQuantity());
    }

}
