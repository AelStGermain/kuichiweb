package cl.kuichi.kuichiweb.controller;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Clinic;
import cl.kuichi.kuichiweb.model.Review;
import cl.kuichi.kuichiweb.service.AppUserService;
import cl.kuichi.kuichiweb.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private AppUserService userService;

    // 1. LISTA (Simple)
    @GetMapping
    public String listClinics(Model model) {
        model.addAttribute("clinics", clinicService.findAll());
        return "clinics/list";
    }

    // 2. VISTA DE DETALLE + RESEÑAS (Tipo Google Maps)
    @GetMapping("/{id}")
    public String showClinicDetail(@PathVariable Long id, Model model) {
        Clinic clinic = clinicService.findById(id);
        model.addAttribute("clinic", clinic);
        model.addAttribute("newReview", new Review()); // Objeto para el formulario de comentario
        return "clinics/detail"; // Nueva vista que crearemos
    }

    // 3. FORMULARIO NUEVO (Completo)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("clinic", new Clinic());
        return "clinics/form";
    }

    // 4. GUARDAR CLÍNICA
    @PostMapping("/save")
    public String saveClinic(@Valid @ModelAttribute Clinic clinic,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "clinics/form"; // Volver al formulario mostrando errores
        }
        clinicService.saveClinic(clinic);
        return "redirect:/clinics";
    }

    // 5. GUARDAR RESEÑA (POST)
    @PostMapping("/{id}/review")
    public String addReview(@PathVariable Long id, @ModelAttribute Review review, Principal principal) {
        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);
        clinicService.addReview(id, currentUser, review);
        return "redirect:/clinics/" + id; // Recargamos la misma página
    }

    // 6. EDITAR
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Clinic clinic = clinicService.findById(id);
        model.addAttribute("clinic", clinic);
        return "clinics/form";
    }

    // 7. ELIMINAR
    @GetMapping("/delete/{id}")
    public String deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return "redirect:/clinics";
    }
}