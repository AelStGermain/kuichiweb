package cl.kuichi.kuichiweb.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username; // Para el login
    private String password;
    private String fullName;
    private String email;
    private String role; // "USER" o "ADMIN"

    // Relación: Un dueño tiene muchas mascotas
    // "mappedBy" indica que la otra clase (Pet) es dueña de la relación
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    // Constructores
    public AppUser() {}

    public AppUser(String username, String password, String fullName, String email, String role) {
        this.username = username;
        this.password = password; // Recuerda: en producción esto iría encriptado
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<Pet> getPets() { return pets; }
    public void setPets(List<Pet> pets) { this.pets = pets; }
}