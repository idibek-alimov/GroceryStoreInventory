package tj.alimov.gorcerystoreinventory.mapper;

import org.mapstruct.Mapper;
import tj.alimov.gorcerystoreinventory.dto.category.CategoryRequestDto;
import tj.alimov.gorcerystoreinventory.dto.category.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);
    Category toEntity(CategoryRequestDto dto);
}
