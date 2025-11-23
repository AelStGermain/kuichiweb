package cl.kuichi.kuichiweb.service;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Clinic;
import cl.kuichi.kuichiweb.model.Review;
import cl.kuichi.kuichiweb.repository.ClinicRepository;
import cl.kuichi.kuichiweb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepo;

    // repositorio de reseñas para poder guardarlas
    @Autowired
    private ReviewRepository reviewRepo;

    // --- MÉTODOS CRUD CLÁSICOS ---

    // READ (Listar todas)
    public List<Clinic> findAll() {
        return clinicRepo.findAll();
    }

    // READ (Buscar una por ID)
    public Clinic findById(Long id) {
        return clinicRepo.findById(id).orElse(null);
    }

    // CREATE & UPDATE (Guardar Clínica)
    public void saveClinic(Clinic clinic) {
        clinicRepo.save(clinic);
    }

    // DELETE (Eliminar Clínica)
    public void deleteClinic(Long id) {
        clinicRepo.deleteById(id);
    }

    /**
     * Guarda una reseña vinculándola a la clínica y al usuario autor.
     */
    public void addReview(Long clinicId, AppUser author, Review review) {
        // 1. Buscamos la clínica por ID
        Clinic clinic = findById(clinicId);
        
        // 2. Si existe, vinculamos todo y guardamos
        if (clinic != null) {
            review.setClinic(clinic); // Decimos "esta reseña es de ESTA clínica"
            review.setAuthor(author); // Decimos "esta reseña la escribió ESTE usuario"
            reviewRepo.save(review);  // Guardamos en la BD
        }
    }
}