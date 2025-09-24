package tj.alimov.gorcerystoreinventory.mapper;

import org.mapstruct.Mapper;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchRequestDto;
import tj.alimov.gorcerystoreinventory.dto.stockBatch.StockBatchResponseDto;
import tj.alimov.gorcerystoreinventory.model.StockBatch;

@Mapper(componentModel = "spring")
public interface StockBatchMapper {
    StockBatchResponseDto toDto(StockBatch batch);
    StockBatch toEntity(StockBatchRequestDto dto);
}
