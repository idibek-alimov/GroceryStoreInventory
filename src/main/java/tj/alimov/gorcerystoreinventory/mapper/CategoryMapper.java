package tj.alimov.gorcerystoreinventory.mapper;

import tj.alimov.gorcerystoreinventory.dto.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.model.Category;

public class CategoryMapper {
    public static CategoryResponseDto toDto(Category category){
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
