package tj.alimov.gorcerystoreinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.alimov.gorcerystoreinventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
