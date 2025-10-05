package tj.alimov.gorcerystoreinventory.product;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tj.alimov.gorcerystoreinventory.dto.category.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.dto.product.ProductRequestDto;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.mapper.ProductMapper;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.model.Product;
import tj.alimov.gorcerystoreinventory.model.Supplier;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;
import tj.alimov.gorcerystoreinventory.repository.ProductRepository;
import tj.alimov.gorcerystoreinventory.repository.SupplierRepository;
import tj.alimov.gorcerystoreinventory.service.ProductService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private Supplier supplier;
    private ProductRequestDto requestDto;
    private ProductResponseDto responseDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        category = new Category(1L, "category", "Category Description", new ArrayList<>());
        supplier = new Supplier(1L, "name", "email", "phone", new ArrayList<>());

        product = Product.builder()
                .id(1L)
                .name("product")
                .minStockLevel(2)
                .category(category)
                .supplier(supplier)
                .build();

        requestDto = new ProductRequestDto("product", 1L, 1L, 2);
        responseDto = new ProductResponseDto(1L,
                                             "product",
                                             CategoryResponseDto.builder().id(1L).build(),
                SupplierResponseDto.builder().id(1L).build(), 2, Instant.now(), Instant.now());
    }

    @Test
    void create_ShouldReturnProductResponseDto(){
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        when(supplierRepository.findById(any(Long.class))).thenReturn(Optional.of(supplier));
        when(productMapper.toEntity(any(ProductRequestDto.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(responseDto);

        ProductResponseDto responseDto = productService.create(requestDto);

        assertNotNull(responseDto);
        assertEquals(responseDto.getName(), "product");
        assertEquals(responseDto.getMinStockLevel(), 2);
        assertEquals(responseDto.getCategory().getId(), 1L);
        assertEquals(responseDto.getSupplier().getId(), 1L);
    }



    @Test
    void getAll_ShouldReturnPageOfProductResponseDto(){
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(product)));
        when(productMapper.toDto(any(Product.class))).thenReturn(responseDto);

        Page<ProductResponseDto> page = productService.getAll(PageRequest.of(0, 10));

        assertNotNull(page);
        assertEquals(1, page.getSize());
        assertEquals("product", page.getContent().get(0).getName());
    }

    @Test
    void getById_ShouldReturnProductResponseDto(){
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(responseDto);

        ProductResponseDto response = productService.getById(1L);

        assertNotNull(response);
        assertEquals("product", response.getName());
        assertEquals(1L, response.getId());
    }

    @Test
    void getById_ShouldThrowException(){
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        when(productMapper.toDto(any(Product.class))).thenReturn(responseDto);


        assertThrows(ResourceNotFoundException.class, () -> productService.getById(99L));
    }

    @Test
    void update_ShouldReturnProductResponseDto(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(productMapper.toDto(product)).thenReturn(responseDto);
        requestDto.setName("productUpdate");
        ProductResponseDto response = productService.update(1L, requestDto);

        assertNotNull(response);
        assertEquals(responseDto.getName(), response.getName());
    }


}
