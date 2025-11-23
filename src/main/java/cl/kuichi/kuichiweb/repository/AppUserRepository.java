package cl.kuichi.kuichiweb.repository;

import cl.kuichi.kuichiweb.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<TipoDeEntidad, TipoDeID>
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
    // ¡MAGIA! Spring implementará esto solo con ver el nombre
    // SELECT * FROM app_user WHERE username = ?
    Optional<AppUser> findByUsername(String username);
    
    // Para evitar duplicados al registrarse
    boolean existsByUsername(String username);
}