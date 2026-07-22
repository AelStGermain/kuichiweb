package cl.kuichi.kuichiweb.service;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder; // Necesitamos definir este Bean

    // Método obligatorio de Spring Security: Carga el usuario desde la BD
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertimos nuestro AppUser al User de Spring Security
        return User.builder()
            .username(appUser.getUsername())
            .password(appUser.getPassword())
            .roles(appUser.getRole())
            .build();
    }

    // Método para registrar usuarios nuevos (encriptando la clave)
    public void registerUser(AppUser user) {
        if (repo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        // Encriptamos la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Por defecto todos son usuarios normales
        repo.save(user);
    }

    public AppUser findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }
}