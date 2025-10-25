package tj.alimov.gorcerystoreinventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tj.alimov.gorcerystoreinventory.dto.supplier.RestockOrderResponseDto;
import tj.alimov.gorcerystoreinventory.model.RestockOrder;

@Mapper(componentModel = "spring")
public interface RestockOrderMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    RestockOrderResponseDto toDto(RestockOrder restockOrder);
}
