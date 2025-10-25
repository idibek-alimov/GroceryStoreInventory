package tj.alimov.gorcerystoreinventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import tj.alimov.gorcerystoreinventory.model.Product;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasSupplier(Long supplierId){
        return (root, query, cb) -> supplierId == null ? null : cb.equal(root.get("supplier").get("id"), supplierId);
    }

    public static Specification<Product> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice != null && maxPrice != null)
                cb.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null)
                cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            if (maxPrice != null)
                cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            return null;
        };
    }

    public static Specification<Product> nameContains(String queryStr) {
        return (root, query, cb) ->
                (queryStr == null || queryStr.isBlank() ? null : cb.like(cb.lower(root.get("name")), "%" + queryStr.toLowerCase() + "%"));
    }
}
