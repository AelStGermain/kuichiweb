package cl.kuichi.kuichiweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <--- IMPORTANTE: Agregar este import
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. RECURSOS ESTÁTICOS (CSS, JS, IMÁGENES)
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                
                // 2. VISTAS PÚBLICAS (Landing page, Login, Registro)
                .requestMatchers("/", "/home").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                
                // 3. OFERTAS (Público)
                .requestMatchers("/offers/**").permitAll() 
                
                // 4. VETERINARIAS (AQUÍ ESTÁ EL CAMBIO CLAVE)
                // Solo permitimos VER la lista (GET) a todo el mundo
                .requestMatchers(HttpMethod.GET, "/clinics").permitAll()
                
                // NOTA: Como no pusimos "/clinics/**" en permitAll, las rutas de 
                // creación (/clinics/new), guardado (/clinics/save) y borrado 
                // caerán en la regla de abajo (.authenticated), protegiéndolas.
                
                // 5. TODO LO DEMÁS REQUIERE LOGIN (CRUD Mascotas, CRUD Veterinarias, Perfil)
                .anyRequest().authenticated() 
            )
            .formLogin(login -> login
                .loginPage("/auth/login")
                .defaultSuccessUrl("/pets", true) // Al entrar, vamos a "Mis Mascotas"
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Al salir, volvemos al inicio
                .permitAll()
            );

        // Configuración para H2 Console
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}