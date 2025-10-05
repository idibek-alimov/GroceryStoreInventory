package tj.alimov.gorcerystoreinventory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierRequestDto;
import tj.alimov.gorcerystoreinventory.dto.supplier.SupplierResponseDto;
import tj.alimov.gorcerystoreinventory.exception.ResourceNotFoundException;
import tj.alimov.gorcerystoreinventory.mapper.SupplierMapper;
import tj.alimov.gorcerystoreinventory.model.Supplier;
import tj.alimov.gorcerystoreinventory.repository.SupplierRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    @Transactional
    public SupplierResponseDto create(SupplierRequestDto dto){
        Supplier supplier = Supplier.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        Supplier saved = supplierRepository.save(supplier);
        return supplierMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public Page<SupplierResponseDto> getAll(Pageable pageable){
        Page<Supplier> supplierPage = supplierRepository.findAll(pageable);
        return supplierPage.map(supplierMapper::toDto);
    }
    @Transactional(readOnly = true)
    public SupplierResponseDto getById(Long id){
        Supplier supplier =  getSupplier(id);
        return supplierMapper.toDto(supplier);
    }
    @Transactional
    public SupplierResponseDto update(Long id, SupplierRequestDto dto){
        Supplier supplier =  getSupplier(id);
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplierRepository.save(supplier);
        return supplierMapper.toDto(supplier);
    }
    @Transactional
    public SupplierResponseDto partialUpdate(Long id, Map<String, Object> updates){
        Supplier supplier = getSupplier(id);
        updates.forEach((field, value) -> {
           switch(field){
               case "name" -> supplier.setName((String) value);
               case "email" -> supplier.setEmail((String) value);
               case "phone" -> supplier.setPhone((String) value);
               default -> throw new IllegalArgumentException("Field " + field + " is not supported for patching");
           }
        });
        return supplierMapper.toDto(supplier);
    }
    @Transactional
    public void delete(Long id){
        if(!supplierRepository.existsById(id)){
            throw new ResourceNotFoundException("Supplier with given ID " + id + " not found");
        }
        supplierRepository.deleteById(id);
    }

    public Supplier getSupplier(Long id){
        return supplierRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Supplier with given ID " + id + " not found"));
    }

}
