package cl.kuichi.kuichiweb.controller.rest;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Pet;
import cl.kuichi.kuichiweb.service.AppUserService;
import cl.kuichi.kuichiweb.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = { "http://localhost:8100", "ionic://localhost" })
public class PetRestController {

    @Autowired
    private PetService petService;

    @Autowired
    private AppUserService userService;

    /**
     * Listar todas las mascotas del usuario autenticado
     * GET /api/pets
     */
    @GetMapping
    public ResponseEntity<List<Pet>> getAllMyPets(Principal principal) {
        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);
        List<Pet> pets = petService.getPetsByOwner(currentUser);
        return ResponseEntity.ok(pets);
    }

    /**
     * Obtener una mascota específica por ID
     * GET /api/pets/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Long id, Principal principal) {
        Pet pet = petService.findById(id);

        if (pet == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Mascota no encontrada");
            return ResponseEntity.status(404).body(error);
        }

        // Verificar que la mascota pertenezca al usuario actual
        String username = principal.getName();
        if (!pet.getOwner().getUsername().equals(username)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No tienes permiso para acceder a esta mascota");
            return ResponseEntity.status(403).body(error);
        }

        return ResponseEntity.ok(pet);
    }

    /**
     * Crear nueva mascota
     * POST /api/pets
     */
    @PostMapping
    public ResponseEntity<?> createPet(@Valid @RequestBody Pet pet, Principal principal) {
        try {
            String username = principal.getName();
            AppUser currentUser = userService.findByUsername(username);
            petService.savePet(pet, currentUser);
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Actualizar mascota existente
     * PUT /api/pets/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id,
            @Valid @RequestBody Pet updatedPet,
            Principal principal) {
        Pet existingPet = petService.findById(id);

        if (existingPet == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Mascota no encontrada");
            return ResponseEntity.status(404).body(error);
        }

        // Verificar que la mascota pertenezca al usuario actual
        String username = principal.getName();
        if (!existingPet.getOwner().getUsername().equals(username)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No tienes permiso para modificar esta mascota");
            return ResponseEntity.status(403).body(error);
        }

        // Actualizar campos
        existingPet.setName(updatedPet.getName());
        existingPet.setSpecies(updatedPet.getSpecies());
        existingPet.setBreed(updatedPet.getBreed());
        existingPet.setBirthDate(updatedPet.getBirthDate());
        existingPet.setMedicalHistory(updatedPet.getMedicalHistory());

        AppUser owner = existingPet.getOwner();
        petService.savePet(existingPet, owner);

        return ResponseEntity.ok(existingPet);
    }

    /**
     * Eliminar mascota
     * DELETE /api/pets/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id, Principal principal) {
        Pet pet = petService.findById(id);

        if (pet == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Mascota no encontrada");
            return ResponseEntity.status(404).body(error);
        }

        // Verificar que la mascota pertenezca al usuario actual
        String username = principal.getName();
        if (!pet.getOwner().getUsername().equals(username)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No tienes permiso para eliminar esta mascota");
            return ResponseEntity.status(403).body(error);
        }

        petService.deletePet(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mascota eliminada exitosamente");
        return ResponseEntity.ok(response);
    }
}
