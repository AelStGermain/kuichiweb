package cl.kuichi.kuichiweb.repository;

import cl.kuichi.kuichiweb.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    // Aquí usaremos findAll() que ya viene incluido
}