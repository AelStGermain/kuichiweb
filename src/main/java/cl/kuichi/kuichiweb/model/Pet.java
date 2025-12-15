package cl.kuichi.kuichiweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotNull(message = "La especie es obligatoria")
    private String species; // Perro, Gato, Exótico

    @Size(max = 100, message = "La raza no puede exceder 100 caracteres")
    private String breed;

    private LocalDate birthDate;

    @Column(length = 500)
    @Size(max = 500, message = "El historial médico no puede exceder 500 caracteres")
    private String medicalHistory; // Notas médicas breves

    // Relación: Muchas mascotas pertenecen a un dueño
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser owner;

    // Constructores
    public Pet() {
    }

    public Pet(String name, String species, String breed, AppUser owner) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.owner = owner;
        this.birthDate = LocalDate.now(); // Por defecto hoy
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
}