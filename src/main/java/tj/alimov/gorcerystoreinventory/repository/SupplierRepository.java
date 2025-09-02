package tj.alimov.gorcerystoreinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.alimov.gorcerystoreinventory.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
