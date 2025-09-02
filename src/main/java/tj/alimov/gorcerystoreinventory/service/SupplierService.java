package tj.alimov.gorcerystoreinventory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tj.alimov.gorcerystoreinventory.model.Supplier;
import tj.alimov.gorcerystoreinventory.repository.SupplierRepository;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Supplier create(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public Supplier getById(Long id){
        return supplierRepository.findById(id).orElseThrow(()-> new RuntimeException("Supplier with given Id not found"));
    }

    public Supplier update(Supplier supplier){
        Supplier oldSupplier = supplierRepository.findById(supplier.getId()).orElseThrow(() -> new RuntimeException(""));
        oldSupplier.setName(supplier.getName());
        oldSupplier.setPhone(supplier.getPhone());
        oldSupplier.setContactEmail(supplier.getContactEmail());
        supplierRepository.save(oldSupplier);
        return oldSupplier;
    }

    public void delete(Long id){
        supplierRepository.deleteById(id);
    }
}
