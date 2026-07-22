package cl.kuichi.kuichiweb.repository;

import cl.kuichi.kuichiweb.model.Pet;
import cl.kuichi.kuichiweb.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    
    // Busca todas las mascotas que pertenezcan a un dueño específico
    // SELECT * FROM pet WHERE user_id = ?
    List<Pet> findByOwner(AppUser owner);
}