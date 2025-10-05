package tj.alimov.gorcerystoreinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierRequestDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierResponseDto;
import tj.alimov.gorcerystoreinventory.service.SupplierService;

import java.util.Map;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping()
    public ResponseEntity<SupplierResponseDto> create(@Valid @RequestBody SupplierRequestDto dto){
        SupplierResponseDto created = supplierService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> getById(@PathVariable("id") Long id){
        SupplierResponseDto dto = supplierService.getById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("")
    public ResponseEntity<Page<SupplierResponseDto>> getAll(Pageable pageable){
        Page<SupplierResponseDto> responsePage = supplierService.getAll(pageable);
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody SupplierRequestDto dto){
        SupplierResponseDto updated = supplierService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> partialUpdate(@PathVariable("id") Long id,
                                                             @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(supplierService.partialUpdate(id, updates));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
