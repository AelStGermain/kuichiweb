package cl.kuichi.kuichiweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilitar @PreAuthorize en REST controllers
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Habilitar CORS
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/h2-console/**") // Deshabilitar CSRF para API
                )
                .authorizeHttpRequests(auth -> auth
                        // 1. RECURSOS ESTÁTICOS (CSS, JS, IMÁGENES)
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // 2. VISTAS PÚBLICAS (Landing page, Login, Registro)
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // 3. OFERTAS (Público)
                        .requestMatchers("/offers/**").permitAll()

                        // 4. CLÍNICAS - Autorización por Roles
                        // Ver lista de clínicas: Público
                        .requestMatchers(HttpMethod.GET, "/clinics").permitAll()

                        // CRUD de clínicas: Solo ADMIN
                        .requestMatchers("/clinics/new", "/clinics/save", "/clinics/edit/**", "/clinics/delete/**")
                        .hasRole("ADMIN")

                        // 5. RESEÑAS - Cualquier usuario autenticado
                        .requestMatchers("/reviews/**").authenticated()

                        // 6. MASCOTAS - Cualquier usuario autenticado
                        .requestMatchers("/pets/**").authenticated()

                        // 7. API REST - Configuración especial
                        // Endpoints públicos de API
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/clinics/**").permitAll()

                        // Resto de endpoints API requieren autenticación
                        .requestMatchers("/api/**").authenticated()

                        // 8. TODO LO DEMÁS REQUIERE LOGIN
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/pets", true) // Al entrar, vamos a "Mis Mascotas"
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Al salir, volvemos al inicio
                        .permitAll());

        // Configuración para H2 Console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}