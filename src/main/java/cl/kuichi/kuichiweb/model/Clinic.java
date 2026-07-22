package cl.kuichi.kuichiweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la clínica es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String address;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String phone;

    @Email(message = "Email inválido")
    private String email;

    @Size(max = 100, message = "El sitio web no puede exceder 100 caracteres")
    private String website;

    @Size(max = 100, message = "El horario no puede exceder 100 caracteres")
    private String openingHours;

    private boolean emergency247;

    // --- CAMPO NUEVO PARA LA FOTO ---
    // Guardaremos la URL de la imagen (ej: https://foto.com/clinica.jpg)
    @Column(length = 500)
    private String imageUrl;

    @Column(length = 1000)
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    // Una clínica tiene muchas ofertas
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    // Una clínica tiene muchas reseñas (Rating system)
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Clinic() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public boolean isEmergency247() {
        return emergency247;
    }

    public void setEmergency247(boolean emergency247) {
        this.emergency247 = emergency247;
    }

    // --- GETTERS Y SETTERS DE LA IMAGEN ---
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // Método auxiliar para calcular promedio de estrellas
    public double getAverageRating() {
        if (reviews.isEmpty())
            return 0.0;
        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        return Math.round((sum / reviews.size()) * 10.0) / 10.0;
    }
}