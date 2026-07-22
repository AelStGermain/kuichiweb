package cl.kuichi.kuichiweb.config;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Clinic;
import cl.kuichi.kuichiweb.model.Offer;
import cl.kuichi.kuichiweb.model.Review;
import cl.kuichi.kuichiweb.repository.AppUserRepository;
import cl.kuichi.kuichiweb.repository.ClinicRepository;
import cl.kuichi.kuichiweb.repository.OfferRepository;
import cl.kuichi.kuichiweb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData(
            AppUserRepository userRepo,
            ClinicRepository clinicRepo,
            OfferRepository offerRepo,
            ReviewRepository reviewRepo) {

        return args -> {
            // 1. USUARIOS DE PRUEBA
            if (userRepo.count() == 0) {
                // Usuario Admin
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullName("Administrador Kuichi");
                admin.setEmail("admin@kuichi.cl");
                admin.setRole("ADMIN");
                userRepo.save(admin);

                // Usuario 1: María López
                AppUser maria = new AppUser();
                maria.setUsername("maria.lopez");
                maria.setPassword(passwordEncoder.encode("pass123"));
                maria.setFullName("María López");
                maria.setEmail("maria@example.com");
                maria.setRole("USER");
                userRepo.save(maria);

                // Usuario 2: Carlos Pérez
                AppUser carlos = new AppUser();
                carlos.setUsername("carlos.perez");
                carlos.setPassword(passwordEncoder.encode("pass123"));
                carlos.setFullName("Carlos Pérez");
                carlos.setEmail("carlos@example.com");
                carlos.setRole("USER");
                userRepo.save(carlos);

                // Usuario 3: Ana Silva
                AppUser ana = new AppUser();
                ana.setUsername("ana.silva");
                ana.setPassword(passwordEncoder.encode("pass123"));
                ana.setFullName("Ana Silva");
                ana.setEmail("ana@example.com");
                ana.setRole("USER");
                userRepo.save(ana);
            }

            // 2. CLÍNICAS REALES DE SANTIAGO
            if (clinicRepo.count() == 0) {

                // Clínica 1: Providencia
                Clinic c1 = new Clinic();
                c1.setName("Hospital Clínico Veterinario U. de Chile");
                c1.setAddress("Av. Cristóbal Colón 3018, Providencia"); // Dirección Real
                c1.setPhone("+56229785555");
                c1.setDescription("Atención de alta complejidad, cirugía y especialidades. Referente nacional.");
                c1.setEmail("contacto@hospitalveterinario.cl");
                c1.setWebsite("www.hospitalveterinario.cl");
                c1.setOpeningHours("24 Horas");
                c1.setEmergency247(true);
                // Veterinario examinando perro
                c1.setImageUrl(
                        "https://images.unsplash.com/photo-1530041539828-114de669390e?q=80&w=1000&auto=format&fit=crop");
                clinicRepo.save(c1);

                // Clínica 2: Maipú
                Clinic c2 = new Clinic();
                c2.setName("Clínica Veterinaria SOS");
                c2.setAddress("Av. Pajaritos 3195, Maipú");
                c2.setPhone("+56987654321");
                c2.setDescription("Medicina preventiva, vacunatorio y peluquería canina.");
                c2.setEmail("contacto@vetsos.cl");
                c2.setWebsite("www.vetsos.cl");
                c2.setOpeningHours("Lun-Sab 09:00 - 20:00");
                c2.setEmergency247(false);
                // Veterinaria con gato en consulta
                c2.setImageUrl(
                        "https://images.unsplash.com/photo-1628009368231-7bb7cfcb0def?q=80&w=1000&auto=format&fit=crop");
                clinicRepo.save(c2);

                // Clínica 3: Vitacura
                Clinic c3 = new Clinic();
                c3.setName("Clínica Alemana Veterinaria");
                c3.setAddress("Av. Vitacura 9999, Vitacura");
                c3.setPhone("+56222223333");
                c3.setDescription("Tecnología de punta para el diagnóstico de tu mascota.");
                c3.setEmail("info@alemanavet.cl");
                c3.setWebsite("www.alemanavet.cl");
                c3.setOpeningHours("24 Horas");
                c3.setEmergency247(true);
                // Veterinario con estetoscopio examinando perro
                c3.setImageUrl(
                        "https://images.unsplash.com/photo-1666214280557-f1b5022eb634?q=80&w=1000&auto=format&fit=crop");
                clinicRepo.save(c3);

                // 3. OFERTAS
                offerRepo.save(new Offer("30% Dscto. Ecografías", "Solo por esta semana.", 30, c1));
                offerRepo.save(new Offer("Chip Gratis", "Al contratar plan anual.", 100, c2));
                offerRepo.save(new Offer("2x1 en Baños", "Para perros pequeños.", 50, c3));

                // 4. RESEÑAS DE DIFERENTES USUARIOS
                AppUser admin = userRepo.findByUsername("admin").orElse(null);
                AppUser maria = userRepo.findByUsername("maria.lopez").orElse(null);
                AppUser carlos = userRepo.findByUsername("carlos.perez").orElse(null);
                AppUser ana = userRepo.findByUsername("ana.silva").orElse(null);

                if (admin != null && maria != null && carlos != null && ana != null) {
                    // Reseñas para Hospital Clínico (c1)
                    Review r1 = new Review();
                    r1.setRating(5);
                    r1.setComment(
                            "Excelente atención, muy profesionales. Mi perro quedó muy bien después de la cirugía.");
                    r1.setClinic(c1);
                    r1.setAuthor(maria);

                    Review r2 = new Review();
                    r2.setRating(4);
                    r2.setComment("Buen servicio, aunque tuve que esperar un poco. Los precios son razonables.");
                    r2.setClinic(c1);
                    r2.setAuthor(carlos);

                    // Reseña para Clínica SOS (c2)
                    Review r3 = new Review();
                    r3.setRating(5);
                    r3.setComment("Mi gato estaba muy enfermo y lo atendieron de urgencia. ¡Gracias por salvarlo!");
                    r3.setClinic(c2);
                    r3.setAuthor(ana);

                    // Reseñas para Clínica Alemana (c3)
                    Review r4 = new Review();
                    r4.setRating(4);
                    r4.setComment("Muy buena clínica, tecnología moderna y veterinarios capacitados.");
                    r4.setClinic(c3);
                    r4.setAuthor(admin);

                    Review r5 = new Review();
                    r5.setRating(3);
                    r5.setComment("Atención correcta pero un poco cara. El servicio es bueno.");
                    r5.setClinic(c3);
                    r5.setAuthor(maria);

                    reviewRepo.save(r1);
                    reviewRepo.save(r2);
                    reviewRepo.save(r3);
                    reviewRepo.save(r4);
                    reviewRepo.save(r5);
                }
            }
        };
    }
}