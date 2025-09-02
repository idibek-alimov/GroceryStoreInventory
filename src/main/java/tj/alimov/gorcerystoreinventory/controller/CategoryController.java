package tj.alimov.gorcerystoreinventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.service.CategoryService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> create(@RequestBody Category category){
        Category createdCategory = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id){
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping()
    public ResponseEntity<Category> update(@RequestBody Category category){
        Category updatedCategory = categoryService.update(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
