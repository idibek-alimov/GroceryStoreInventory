package tj.alimov.gorcerystoreinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.alimov.gorcerystoreinventory.model.StockBatch;

public interface StockBatchRepository extends JpaRepository<StockBatch, Long> {
}
