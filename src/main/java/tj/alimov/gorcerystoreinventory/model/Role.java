package tj.alimov.gorcerystoreinventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "user_role")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Override
    public String getAuthority() {
        return name;
    }
}
