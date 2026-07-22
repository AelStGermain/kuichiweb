package cl.kuichi.kuichiweb.repository;

import cl.kuichi.kuichiweb.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    
    // Podríamos agregar un buscador por nombre:
    // List<Clinic> findByNameContainingIgnoreCase(String name);
}