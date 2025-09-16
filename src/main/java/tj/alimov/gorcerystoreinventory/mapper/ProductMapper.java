package tj.alimov.gorcerystoreinventory.mapper;

import org.mapstruct.Mapper;
import tj.alimov.gorcerystoreinventory.dto.product.ProductRequestDto;
import tj.alimov.gorcerystoreinventory.dto.product.ProductResponseDto;
import tj.alimov.gorcerystoreinventory.model.Product;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toDto(Product product);
    Product toEntity(ProductRequestDto dto);
}
