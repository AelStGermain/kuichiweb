package cl.kuichi.kuichiweb.controller;

import cl.kuichi.kuichiweb.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private PublicService publicService;

    // Página de Inicio (Landing Page)
    @GetMapping("/")
    public String home(Model model) {
        // Cargamos ofertas y clínicas para el home
        model.addAttribute("offers", publicService.getAllOffers());
        model.addAttribute("clinics", publicService.getAllClinics());
        return "home"; 
    }

    // Página específica de Ofertas
    @GetMapping("/offers")
    public String showOffers(Model model) {
        model.addAttribute("offers", publicService.getAllOffers());
        return "offers";
    }

    // --- AQUI BORRAMOS EL MÉTODO showClinics ---
    // ¿Por qué? Porque ahora ClinicController se encarga de esa ruta ("/clinics")
}