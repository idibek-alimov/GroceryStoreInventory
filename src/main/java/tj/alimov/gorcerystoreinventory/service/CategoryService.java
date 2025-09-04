package tj.alimov.gorcerystoreinventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.gorcerystoreinventory.mapper.CategoryMapper;
import tj.alimov.gorcerystoreinventory.dto.CategoryRequestDto;
import tj.alimov.gorcerystoreinventory.dto.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDto create(CategoryRequestDto dto){
        Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAll(Pageable page){
        Page<Category> categoryPage = categoryRepository.findAll(page);
        return categoryPage.map(CategoryMapper::toDto);
    }
    @Transactional(readOnly = true)
    public CategoryResponseDto getById(Long id){
        Category category =  getCategory(id);
        return CategoryMapper.toDto(category);
    }
    @Transactional
    public CategoryResponseDto update(Long id, CategoryRequestDto dto){
        Category category =  getCategory(id);
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }
    @Transactional
    public void delete(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category with given ID " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
    private Category getCategory(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category with given ID " + id + " not found"));
    }
}
