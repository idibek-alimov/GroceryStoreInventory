package tj.alimov.gorcerystoreinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.alimov.gorcerystoreinventory.model.RestockOrder;

public interface RestockOrderRepository extends JpaRepository<RestockOrder, Long> {
}
