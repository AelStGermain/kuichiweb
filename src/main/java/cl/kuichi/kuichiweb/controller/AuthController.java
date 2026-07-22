package cl.kuichi.kuichiweb.controller;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AppUserService userService;

    // Mostrar formulario de Login
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    // Mostrar formulario de Registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "auth/register";
    }

    // Procesar el Registro
    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser user) {
        try {
            userService.registerUser(user);
            return "redirect:/auth/login?registered"; // Éxito
        } catch (Exception e) {
            return "redirect:/auth/register?error"; // Fallo (usuario duplicado, etc.)
        }
    }
}