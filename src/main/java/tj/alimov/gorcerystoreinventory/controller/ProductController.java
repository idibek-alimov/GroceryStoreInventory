package tj.alimov.gorcerystoreinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.dto.product.ProductRequestDto;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("products/")
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

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<ProductResponseDto>> getAll(@PathVariable("page") Integer page,
                                                            @PathVariable("size") Integer size){
        Page<ProductResponseDto> responsePage = productService.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductRequestDto dto){
        ProductResponseDto updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
