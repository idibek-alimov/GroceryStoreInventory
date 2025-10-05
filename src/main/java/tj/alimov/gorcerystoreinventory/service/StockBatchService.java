package tj.alimov.gorcerystoreinventory.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchRequestDto;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.mapper.StockBatchMapper;
import tj.alimov.gorcerystoreinventory.model.Product;
import tj.alimov.gorcerystoreinventory.model.StockBatch;
import tj.alimov.gorcerystoreinventory.repository.ProductRepository;
import tj.alimov.gorcerystoreinventory.repository.StockBatchRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockBatchService {
    private final StockBatchRepository stockBatchRepository;
    private final ProductRepository productRepository;

    private final StockBatchMapper stockBatchMapper;

    @Transactional
    public StockBatchResponseDto create(StockBatchRequestDto dto){
        Product product = getProduct(dto.getProductId());
        StockBatch batch = StockBatch.builder()
                .product(product)
                .quantity(dto.getQuantity())
                .expiryDate(dto.getExpiryDate())
                .receivedDate(dto.getReceivedDate())
                .build();

        stockBatchRepository.save(batch);
        return stockBatchMapper.toDto(batch);
    }

    @Transactional(readOnly = true)
    public StockBatchResponseDto getById(Long id){
        StockBatch stockBatch = getStockBatch(id);
        return stockBatchMapper.toDto(stockBatch);
    }
    @Transactional(readOnly = true)
    public Page<StockBatchResponseDto> getAll(Pageable pageable){
        Page<StockBatch> page = stockBatchRepository.findAll(pageable);
        return page.map(stockBatchMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Page<StockBatchResponseDto> getByProduct(Long id, Pageable pageRequest){
        Page<StockBatch> page = stockBatchRepository.getByProduct(id, pageRequest);
        return page.map(stockBatchMapper::toDto);
    }

    @Transactional
    public StockBatchResponseDto update(Long id, StockBatchRequestDto dto){
        StockBatch stockBatch = getStockBatch(id);
        if(stockBatch.getProduct().getId() != dto.getProductId()){
            Product product = getProduct(dto.getProductId());
            stockBatch.setProduct(product);
        }
        stockBatch.setQuantity(dto.getQuantity());
        stockBatch.setExpiryDate(dto.getExpiryDate());
        stockBatch.setReceivedDate(dto.getReceivedDate());

        return stockBatchMapper.toDto(stockBatch);
    }
    @Transactional
    public StockBatchResponseDto partialUpdate(Long id,
                                               Map<String, Object> updates){
        StockBatch stockBatch = getStockBatch(id);
        updates.forEach((field, value) -> {
            switch(field){
                case "productId" -> stockBatch.setProduct(getProduct((Long) value));
                case "quantity" -> stockBatch.setQuantity((Integer) value);
                case "expiryDate" -> stockBatch.setExpiryDate(Instant.parse(value.toString()));
                case "receivedDate" -> stockBatch.setReceivedDate(Instant.parse(value.toString()));
                default -> throw new IllegalArgumentException("Field " + field + " is not supported for patching");
            }
        });
        return stockBatchMapper.toDto(stockBatch);
    }

    @Transactional
    public void delete(Long id){
        if(!stockBatchRepository.existsById(id)){
            throw new ResourceNotFoundException("StockBatch with id " + id + " not found");
        }
        stockBatchRepository.deleteById(id);
    }

    private StockBatch getStockBatch(Long id){
        return stockBatchRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("StockBatch with id " + id + " not found"));
    }
    private Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product with id " + id + " not found"));
    }
}
