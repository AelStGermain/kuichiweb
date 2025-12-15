package cl.kuichi.kuichiweb.controller;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Pet;
import cl.kuichi.kuichiweb.service.AppUserService;
import cl.kuichi.kuichiweb.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private AppUserService userService;

    // 1. LISTAR (READ) - Solo mis mascotas
    @GetMapping
    public String listMyPets(Model model, Principal principal) {
        // 'Principal' contiene el nombre del usuario logueado
        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);

        model.addAttribute("pets", petService.getPetsByOwner(currentUser));
        model.addAttribute("user", currentUser);
        return "pets/list";
    }

    // 2. FORMULARIO NUEVO (CREATE - GET)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "pets/form";
    }

    // 3. GUARDAR (CREATE/UPDATE - POST)
    @PostMapping("/save")
    public String savePet(@Valid @ModelAttribute Pet pet,
            BindingResult result,
            Principal principal,
            Model model) {
        if (result.hasErrors()) {
            return "pets/form"; // Volver al formulario mostrando errores
        }

        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);

        // Importante: Asignamos el dueño antes de guardar
        petService.savePet(pet, currentUser);
        return "redirect:/pets";
    }

    // 4. EDITAR (UPDATE - GET)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Pet pet = petService.findById(id);
        // Seguridad básica: Verificar que la mascota sea del usuario actual podría ir
        // aquí
        model.addAttribute("pet", pet);
        return "pets/form";
    }

    // 5. ELIMINAR (DELETE)
    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return "redirect:/pets";
    }
}