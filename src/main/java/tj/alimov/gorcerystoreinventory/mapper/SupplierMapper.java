package tj.alimov.gorcerystoreinventory.mapper;

import tj.alimov.gorcerystoreinventory.dto.SupplierResponseDto;
import tj.alimov.gorcerystoreinventory.model.Supplier;

public class SupplierMapper {
    public static SupplierResponseDto toDto(Supplier supplier){
        return SupplierResponseDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .build();
    }
}
