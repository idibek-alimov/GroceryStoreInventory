package tj.alimov.gorcerystoreinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.dto.RestockOrderRequestDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.RestockOrderResponseDto;
import tj.alimov.gorcerystoreinventory.service.RestockOrderService;

import java.util.Map;

@RestController
@RequestMapping("/restock-orders")
@RequiredArgsConstructor
public class RestockOrderController {
    private final RestockOrderService restockOrderService;

    @PostMapping
    public ResponseEntity<RestockOrderResponseDto> create(@Valid @RequestBody RestockOrderRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(restockOrderService.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<Page<RestockOrderResponseDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(restockOrderService.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestockOrderResponseDto> update(@PathVariable("id") Long id, @Valid @RequestBody RestockOrderRequestDto dto){
        RestockOrderResponseDto responseDto = restockOrderService.update(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<RestockOrderResponseDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(restockOrderService.partialUpdate(id, updates));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        restockOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
