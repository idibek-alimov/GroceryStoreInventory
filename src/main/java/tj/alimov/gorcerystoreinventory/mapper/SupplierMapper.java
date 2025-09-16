package tj.alimov.gorcerystoreinventory.mapper;

import org.mapstruct.Mapper;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierRequestDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierResponseDto;
import tj.alimov.gorcerystoreinventory.model.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierResponseDto toDto(Supplier supplier);
    Supplier toEntity(SupplierRequestDto dto);
}
