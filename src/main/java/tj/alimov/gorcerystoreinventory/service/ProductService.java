package tj.alimov.gorcerystoreinventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.gorcerystoreinventory.dto.product.ProductRequestDto;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.mapper.ProductMapper;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.model.Product;
import tj.alimov.gorcerystoreinventory.model.Supplier;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;
import tj.alimov.gorcerystoreinventory.repository.ProductRepository;
import tj.alimov.gorcerystoreinventory.repository.SupplierRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponseDto create(ProductRequestDto dto){
        Category category = getCategory(dto.getCategoryId());
        Supplier supplier = getSupplier(dto.getSupplierId());

        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        product.setSupplier(supplier);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAll(Pageable page){
        Page<Product> productPage = productRepository.findAll(page);
        return productPage.map(productMapper::toDto);
    }
    @Transactional(readOnly = true)
    public ProductResponseDto getById(Long id){
        Product product =  getProduct(id);
        return productMapper.toDto(product);
    }
    @Transactional
    public ProductResponseDto update(Long id, ProductRequestDto dto){
        Product product =  getProduct(id);

        product.setName(dto.getName());
        product.setMinStockLevel(dto.getMinStockLevel());

        if(!product.getCategory().getId().equals(dto.getCategoryId())){
            Category category = getCategory(dto.getCategoryId());
            product.setCategory(category);
        }

        if(!product.getSupplier().getId().equals(dto.getSupplierId())){
            Supplier supplier = getSupplier(dto.getSupplierId());
            product.setSupplier(supplier);
        }

        return productMapper.toDto(product);
    }
    @Transactional
    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }
    private Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product with id " + id + " not found"));
    }
    private Category getCategory(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }
    private Supplier getSupplier(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));
    }

}
