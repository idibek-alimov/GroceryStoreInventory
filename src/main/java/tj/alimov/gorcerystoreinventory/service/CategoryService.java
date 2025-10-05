package tj.alimov.gorcerystoreinventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.gorcerystoreinventory.mapper.CategoryMapper;
import tj.alimov.gorcerystoreinventory.dto.category.CategoryRequestDto;
import tj.alimov.gorcerystoreinventory.dto.category.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Transactional
    public CategoryResponseDto create(CategoryRequestDto dto){
        Category category = categoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAll(Pageable page){
        Page<Category> categoryPage = categoryRepository.findAll(page);
        return categoryPage.map(categoryMapper::toDto);
    }
    @Transactional(readOnly = true)
    public CategoryResponseDto getById(Long id){
        Category category =  getCategory(id);
        return categoryMapper.toDto(category);
    }
    @Transactional
    public CategoryResponseDto update(Long id, CategoryRequestDto dto){
        Category category =  getCategory(id);
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }
    @Transactional
    public CategoryResponseDto partialUpdate(Long id, Map<String, Object> updates){
        Category category = getCategory(id);

        updates.forEach((field, value) -> {
            switch(field){
                case "name" -> category.setName((String) value);
                case "description" -> category.setDescription((String) value);
                default -> throw new IllegalArgumentException("Field" + field + " not supported for patching");
            }
        });
        return categoryMapper.toDto(category);
    }
    @Transactional
    public void delete(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category with given ID " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
    public Category getCategory(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category with given ID " + id + " not found"));
    }
}
