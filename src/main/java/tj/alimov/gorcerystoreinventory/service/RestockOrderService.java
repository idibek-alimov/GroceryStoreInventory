package tj.alimov.gorcerystoreinventory.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.gorcerystoreinventory.dto.RestockOrderRequestDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.RestockOrderResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.mapper.RestockOrderMapper;
import tj.alimov.gorcerystoreinventory.model.Product;
import tj.alimov.gorcerystoreinventory.model.RestockOrder;
import tj.alimov.gorcerystoreinventory.model.Status;
import tj.alimov.gorcerystoreinventory.repository.ProductRepository;
import tj.alimov.gorcerystoreinventory.repository.RestockOrderRepository;
import tj.alimov.gorcerystoreinventory.repository.StockBatchRepository;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RestockOrderService {
    private final RestockOrderRepository restockOrderRepository;
    private final StockBatchRepository stockBatchRepository;
    private final ProductRepository productRepository;
    private final RestockOrderMapper restockOrderMapper;

    @Transactional
    public RestockOrderResponseDto create(RestockOrderRequestDto dto){
        Product product = getProduct(dto.getProductId());
        RestockOrder restockOrder = RestockOrder.builder()
                .product(product)
                .quantity(dto.getQuantity())
                .status(Status.PENDING)
                .build();

        restockOrderRepository.save(restockOrder);
        return restockOrderMapper.toDto(restockOrder);
    }

    @Transactional(readOnly = true)
    public Page<RestockOrderResponseDto> getAll(Pageable pageable){
        return restockOrderRepository.findAll(pageable).map(restockOrderMapper::toDto);
    }

    @Transactional
    public RestockOrderResponseDto update(Long id, RestockOrderRequestDto dto){
        RestockOrder restockOrder = getRestockOrder(id);
        restockOrder.setQuantity(dto.getQuantity());
        // Check if product changed, then reset it
        if(restockOrder.getProduct().getId() != dto.getProductId()){
            Product product = getProduct(dto.getProductId());
            restockOrder.setProduct(product);
        }

        return restockOrderMapper.toDto(restockOrder);
    }
    @Transactional
    public RestockOrderResponseDto partialUpdate(Long id, Map<String, Object> updates){
        RestockOrder restockOrder = getRestockOrder(id);

        updates.forEach((field, value) -> {
            switch(field){
                case "quantity" -> restockOrder.setQuantity((Integer) value);
                case "productId" -> restockOrder.setProduct(getProduct((Long) value));
                default -> throw new IllegalArgumentException("Field " + field + " is not supported for patching");
            }
        });
        return restockOrderMapper.toDto(restockOrder);
    }

    @Transactional
    public void delete(Long id){
        if (!restockOrderRepository.existsById(id)) {
            throw new ResourceNotFoundException("RestockOrder with id " + id + " not found");
        }
        restockOrderRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void generateRestockOrders(){
        productRepository.findAll().forEach(product -> {
            Integer totalStock = stockBatchRepository.getTotalStockByProductId(product.getId());
            if(totalStock != null && totalStock < product.getMinStockLevel()){
                RestockOrder order = RestockOrder.builder()
                        .product(product)
                        .quantity(product.getMinStockLevel() - totalStock)
                        .status(Status.PENDING)
                        .build();

                restockOrderRepository.save(order);
            }
        });
    }
    private RestockOrder getRestockOrder(Long id){
        return restockOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RestockOrder with id " + id + " not found"));
    }
    private Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product with id " + id + " not found"));
    }
}
