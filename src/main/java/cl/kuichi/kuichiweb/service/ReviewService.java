package cl.kuichi.kuichiweb.service;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Clinic;
import cl.kuichi.kuichiweb.model.Review;
import cl.kuichi.kuichiweb.repository.ClinicRepository;
import cl.kuichi.kuichiweb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ClinicRepository clinicRepo;

    /**
     * Agregar nueva reseña a una clínica
     */
    public void addReview(Long clinicId, AppUser author, Review review) {
        Clinic clinic = clinicRepo.findById(clinicId).orElse(null);

        if (clinic != null) {
            review.setClinic(clinic);
            review.setAuthor(author);
            reviewRepo.save(review);
        }
    }

    /**
     * Buscar reseña por ID
     */
    public Review findById(Long id) {
        return reviewRepo.findById(id).orElse(null);
    }

    /**
     * Actualizar reseña existente (solo si el usuario es el autor)
     */
    public void updateReview(Long reviewId, Review updatedReview, AppUser currentUser) {
        Review existingReview = findById(reviewId);

        if (existingReview != null && isOwner(existingReview, currentUser)) {
            existingReview.setRating(updatedReview.getRating());
            existingReview.setComment(updatedReview.getComment());
            reviewRepo.save(existingReview);
        }
    }

    /**
     * Eliminar reseña (solo si el usuario es el autor)
     */
    public void deleteReview(Long reviewId, AppUser currentUser) {
        Review review = findById(reviewId);

        if (review != null && isOwner(review, currentUser)) {
            reviewRepo.deleteById(reviewId);
        }
    }

    /**
     * Verificar si el usuario es el autor de la reseña
     */
    public boolean isOwner(Review review, AppUser user) {
        return review.getAuthor().getId().equals(user.getId());
    }
}
