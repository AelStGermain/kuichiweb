package cl.kuichi.kuichiweb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species; // Perro, Gato, Exótico
    private String breed;
    private LocalDate birthDate;
    
    @Column(length = 500)
    private String medicalHistory; // Notas médicas breves

    // Relación: Muchas mascotas pertenecen a un dueño
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser owner;

    // Constructores
    public Pet() {}

    public Pet(String name, String species, String breed, AppUser owner) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.owner = owner;
        this.birthDate = LocalDate.now(); // Por defecto hoy
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public AppUser getOwner() { return owner; }
    public void setOwner(AppUser owner) { this.owner = owner; }
}