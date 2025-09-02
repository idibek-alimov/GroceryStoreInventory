package tj.alimov.gorcerystoreinventory.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@MappedSuperclass
public class Auditable {
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updateAt;
}
