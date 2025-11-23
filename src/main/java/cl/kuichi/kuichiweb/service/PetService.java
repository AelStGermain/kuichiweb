package cl.kuichi.kuichiweb.service;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Pet;
import cl.kuichi.kuichiweb.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepo;

    // Obtener solo las mascotas de un dueño específico
    public List<Pet> getPetsByOwner(AppUser owner) {
        return petRepo.findByOwner(owner);
    }

    // Guardar mascota vinculándola al dueño
    public void savePet(Pet pet, AppUser owner) {
        pet.setOwner(owner); // Asignamos el dueño automáticamente
        petRepo.save(pet);
    }

    public Pet findById(Long id) {
        return petRepo.findById(id).orElse(null);
    }

    public void deletePet(Long id) {
        petRepo.deleteById(id);
    }
}