package tj.alimov.gorcerystoreinventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Supplier extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "supplier")
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        this.products.add(product);
        product.setSupplier(this);
    }
    public void removeProduct(Product product){
        this.products.remove(product);
        product.setSupplier(null);
    }
}
