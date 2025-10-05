package tj.alimov.gorcerystoreinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.dto.product.ProductRequestDto;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.service.ProductService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductRequestDto dto){
        ProductResponseDto created = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") Long id){
        ProductResponseDto dto = productService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductResponseDto>> getAll(Pageable pageable){
        Page<ProductResponseDto> responsePage = productService.getAll(pageable);
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductRequestDto dto){
        ProductResponseDto updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("{id}")
    public ResponseEntity<ProductResponseDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates
            ){
        return ResponseEntity.ok(productService.partialUpdate(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
