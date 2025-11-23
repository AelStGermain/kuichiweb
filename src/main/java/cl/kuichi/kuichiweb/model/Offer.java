package cl.kuichi.kuichiweb.model;

import jakarta.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Ej: "2x1 en Vacunas"
    private String description;
    private Integer discountPercentage; // Ej: 20
    
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public Offer() {}

    public Offer(String title, String description, Integer discountPercentage, Clinic clinic) {
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.clinic = clinic;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(Integer discountPercentage) { this.discountPercentage = discountPercentage; }
    public Clinic getClinic() { return clinic; }
    public void setClinic(Clinic clinic) { this.clinic = clinic; }
}