package tj.alimov.gorcerystoreinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tj.alimov.gorcerystoreinventory.model.StockBatch;

public interface StockBatchRepository extends JpaRepository<StockBatch, Long> {

    @Query(value = "SELECT sb FROM StockBatch sb WHERE sb.product.id = :id",
            countQuery = "SELECT COUNT(sb) FROM StockBatch sb")
    Page<StockBatch> getByProduct(@Param("productId") Long id,@Param("pageable") Pageable pageable);

    @Query(value = "SELECT SUM(sb.quantity) FROM StockBatch sb WHERE sb.product.id = :productId")
    Integer getTotalStockByProductId(@Param("productId") Long productId);
}
