package tj.alimov.gorcerystoreinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchRequestDto;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchResponseDto;
import tj.alimov.gorcerystoreinventory.service.StockBatchService;

@RestController
@RequestMapping("/stock-batches")
@RequiredArgsConstructor
public class StockBatchController {
    private final StockBatchService stockBatchService;


    @PostMapping
    public ResponseEntity<StockBatchResponseDto> create(@Valid @RequestBody StockBatchRequestDto dto){
        StockBatchResponseDto response = stockBatchService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockBatchResponseDto> getById(@PathVariable("id") Long id){
        StockBatchResponseDto response = stockBatchService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<StockBatchResponseDto>> getAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Page<StockBatchResponseDto> pageResponse = stockBatchService.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/product/{id}/{page}/{size}")
    public ResponseEntity<Page<StockBatchResponseDto>> findByProduct(@PathVariable("id") Long id,
                                                                     @PathVariable("page") Integer page,
                                                                     @PathVariable("size") Integer size){
        Page<StockBatchResponseDto> pageResponse = stockBatchService.getByProduct(id, PageRequest.of(page, size));
        return ResponseEntity.ok(pageResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockBatchResponseDto> update(@PathVariable("id") Long id,
                                                       @Valid @RequestBody StockBatchRequestDto dto){
        StockBatchResponseDto response = stockBatchService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        stockBatchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
