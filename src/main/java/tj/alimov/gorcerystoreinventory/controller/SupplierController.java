package tj.alimov.gorcerystoreinventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.model.Supplier;
import tj.alimov.gorcerystoreinventory.service.SupplierService;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping()
    public ResponseEntity<Supplier> create(@RequestBody Supplier supplier){
        Supplier createdSupplier = supplierService.create(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(@PathVariable("id") Long id){
        Supplier supplier = supplierService.getById(id);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping()
    public ResponseEntity<Supplier> update(@RequestBody Supplier supplier){
        Supplier updatedSupplier = supplierService.update(supplier);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
