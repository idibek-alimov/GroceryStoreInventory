package tj.alimov.gorcerystoreinventory.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import tj.alimov.gorcerystoreinventory.dto.CategoryRequestDto;
import tj.alimov.gorcerystoreinventory.dto.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;
import tj.alimov.gorcerystoreinventory.service.CategoryService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequestDto requestDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        category = Category.builder()
                .id(1L)
                .name("Fruits")
                .description("Fresh fruits")
                .build();

        requestDto = new CategoryRequestDto("Fruits", "Fresh Fruits");
    }

    @Test
    void create_ShouldReturnCategoryResponseDto(){
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryResponseDto responseDto = categoryService.create(requestDto);

        assertNotNull(responseDto);
        assertEquals("Fruits", responseDto.getName());
        assertEquals("Fresh fruits", responseDto.getDescription());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void getById_ShouldReturnCategoryResponseDto(){
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        CategoryResponseDto responseDto = categoryService.getById(1L);

        assertNotNull(responseDto);
        assertEquals("Fruits", responseDto.getName());
        verify(categoryRepository, times(1)).findById(any(Long.class));
    }
    @Test
    void getById_ShouldThrowException_WhenNotFound(){
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getById(99L));


    }

    @Test
    void update_ShouldModifyAndReturnUpdateCategory(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryRequestDto updateDto = new CategoryRequestDto("Veggies", "Green and leafy");
        CategoryResponseDto response = categoryService.update(1L, updateDto);

        assertNotNull(response);
        assertEquals(response.getName(), "Veggies");
        assertEquals(response.getDescription(), "Green and leafy");
        verify(categoryRepository).save(category);
    }

    @Test
    void delete_ShouldRemoveCategory(){
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.delete(1L);
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenCategoryNotFound(){
        when(categoryRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()-> categoryService.delete(99L));
    }

    @Test
    void getAll_ShouldReturnListOfCategoryResponseDTOs(){
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(category)));

        Page<CategoryResponseDto> page = categoryService.getAll(PageRequest.of(0, 10));

        assertEquals(1, page.getTotalElements());
        assertEquals("Fruits", page.getContent().get(0).getName());
    }

}
