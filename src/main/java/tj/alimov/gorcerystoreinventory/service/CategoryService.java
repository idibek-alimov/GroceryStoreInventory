package tj.alimov.gorcerystoreinventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category create(Category category){
        return categoryRepository.save(category);
    }

    public Category getById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category with given Id not found"));
    }
    public Category update(Category category){
        Category oldCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new RuntimeException(""));
        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());
        categoryRepository.save(oldCategory);
        return oldCategory;
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
