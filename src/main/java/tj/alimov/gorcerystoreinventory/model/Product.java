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
public class Product extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private Integer minStockLevel;

    @OneToMany(mappedBy = "product")
    List<RestockOrder> restockOrders = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<StockBatch> stockBatches = new ArrayList<>();

    public void addRestockOrder(RestockOrder restockOrder){
        this.restockOrders.add(restockOrder);
        restockOrder.setProduct(this);
    }
    public void addStockBatch(StockBatch stockBatch){
        this.stockBatches.add(stockBatch);
        stockBatch.setProduct(this);
    }
    public void removeRestockOrder(RestockOrder restockOrder){
        this.restockOrders.remove(restockOrder);
        restockOrder.setProduct(null);
    }
    public void removeStockBatch(StockBatch stockBatch){
        this.stockBatches.remove(stockBatch);
        stockBatch.setProduct(null);
    }
}
