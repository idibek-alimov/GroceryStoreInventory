package tj.alimov.gorcerystoreinventory.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import tj.alimov.gorcerystoreinventory.model.Category;
import tj.alimov.gorcerystoreinventory.model.Product;
import tj.alimov.gorcerystoreinventory.model.Supplier;
import tj.alimov.gorcerystoreinventory.repository.CategoryRepository;
import tj.alimov.gorcerystoreinventory.repository.ProductRepository;
import tj.alimov.gorcerystoreinventory.repository.SupplierRepository;
import tj.alimov.gorcerystoreinventory.specifications.ProductSpecification;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private Category electronics;
    private Supplier apple;

    @BeforeEach
    void setup() {
        electronics = categoryRepository.save(new Category(null, "Electronics", "", null));
        apple = supplierRepository.save(new Supplier(null, "Apple", "apple@example.com", "123456789", new ArrayList<>()));
        productRepository.save(new Product(null, "iPhone 14", electronics, apple,1, null, null));
        productRepository.save(new Product(null, "Samsung S4", electronics, apple,1, null, null));
//        productRepository.save(new Product(null, "MacBook Pro", electronics, apple, 2, null, null));
//        productRepository.save(new Product(null, "AirPods", electronics, apple, 3, null, null));

    }

    @Test
    void findByCategory() {
        Specification<Product> spec = Specification.unrestricted();
        spec.and(ProductSpecification.hasCategory(electronics.getId()));
        List<Product> results = productRepository.findAll(spec);

        assertThat(results).extracting(Product::getName).containsExactly("iPhone 14");
    }

    @Test
    void findBySuppllier() {
        Specification<Product> spec = Specification.unrestricted();
        spec.and(ProductSpecification.hasSupplier(apple.getId()));
        List<Product> results = productRepository.findAll(spec);

        assertThat(results).extracting(Product::getName).containsExactly("iPhone 14");
    }

    @Test
    void findByName() {
//        Specification<Product> spec = Specification.unrestricted();
//        spec.and(ProductSpecification.nameContains("sams"));
//        List<Product> results = productRepository.findAll(spec);
//
//        assertThat(results).extracting(Product::getName).doesNotContain("iPhone 14");
    }
}
