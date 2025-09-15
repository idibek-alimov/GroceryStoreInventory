package tj.alimov.gorcerystoreinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.dto.CategoryRequestDto;
import tj.alimov.gorcerystoreinventory.dto.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.service.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody CategoryRequestDto dto){
        CategoryResponseDto created = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable("id") Long id){
        CategoryResponseDto dto = categoryService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<CategoryResponseDto>> getAll(@PathVariable("page") Integer page,
                                                            @PathVariable("size") Integer size){
        Page<CategoryResponseDto> responsePage = categoryService.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryRequestDto dto){
        CategoryResponseDto updated = categoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
