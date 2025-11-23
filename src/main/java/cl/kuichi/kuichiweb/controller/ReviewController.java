package cl.kuichi.kuichiweb.controller;

import cl.kuichi.kuichiweb.model.AppUser;
import cl.kuichi.kuichiweb.model.Review;
import cl.kuichi.kuichiweb.service.AppUserService;
import cl.kuichi.kuichiweb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AppUserService userService;

    /**
     * Agregar nueva reseña a una clínica
     */
    @PostMapping("/add/{clinicId}")
    public String addReview(@PathVariable Long clinicId,
            @RequestParam int rating,
            @RequestParam String comment,
            Principal principal) {
        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);

        reviewService.addReview(clinicId, currentUser, review);

        return "redirect:/clinics";
    }

    /**
     * Actualizar reseña existente
     */
    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable Long reviewId,
            @RequestParam int rating,
            @RequestParam String comment,
            Principal principal) {
        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);

        Review updatedReview = new Review();
        updatedReview.setRating(rating);
        updatedReview.setComment(comment);

        reviewService.updateReview(reviewId, updatedReview, currentUser);

        return "redirect:/clinics";
    }

    /**
     * Eliminar reseña
     */
    @GetMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId, Principal principal) {
        String username = principal.getName();
        AppUser currentUser = userService.findByUsername(username);

        reviewService.deleteReview(reviewId, currentUser);

        return "redirect:/clinics";
    }
}
