package cl.kuichi.kuichiweb.repository;

import cl.kuichi.kuichiweb.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // JPA nos da todo lo necesario automáticamente
}