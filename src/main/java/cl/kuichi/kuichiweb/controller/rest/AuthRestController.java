package cl.kuichi.kuichiweb.controller.rest;

import cl.kuichi.kuichiweb.dto.LoginDTO;
import cl.kuichi.kuichiweb.dto.MessageResponse;
import cl.kuichi.kuichiweb.dto.RegisterDTO;
import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:8100", "ionic://localhost" })
public class AuthRestController {

    @Autowired
    private AppUserService userService;

    /**
     * Registro de nuevo usuario
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        try {
            AppUser user = new AppUser();
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setFullName(dto.getFullName());
            user.setEmail(dto.getEmail());

            userService.registerUser(user);

            return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente"));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtener información del usuario autenticado
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No autenticado");
            return ResponseEntity.status(401).body(error);
        }

        String username = auth.getName();
        AppUser user = userService.findByUsername(username);

        if (user == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Usuario no encontrado");
            return ResponseEntity.status(404).body(error);
        }

        // Retornar datos del usuario (sin contraseña)
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("username", user.getUsername());
        userData.put("fullName", user.getFullName());
        userData.put("email", user.getEmail());
        userData.put("role", user.getRole());

        return ResponseEntity.ok(userData);
    }
}
