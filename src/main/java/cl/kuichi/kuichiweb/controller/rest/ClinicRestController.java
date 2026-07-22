package cl.kuichi.kuichiweb.controller.rest;

import cl.kuichi.kuichiweb.model.Clinic;
import cl.kuichi.kuichiweb.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clinics")
@CrossOrigin(origins = { "http://localhost:8100", "ionic://localhost" })
public class ClinicRestController {

    @Autowired
    private ClinicService clinicService;

    /**
     * Listar todas las clínicas (público)
     * GET /api/clinics
     */
    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        List<Clinic> clinics = clinicService.findAll();
        return ResponseEntity.ok(clinics);
    }

    /**
     * Obtener detalle de una clínica
     * GET /api/clinics/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClinicById(@PathVariable Long id) {
        Clinic clinic = clinicService.findById(id);

        if (clinic == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Clínica no encontrada");
            return ResponseEntity.status(404).body(error);
        }

        return ResponseEntity.ok(clinic);
    }

    /**
     * Crear nueva clínica (solo ADMIN)
     * POST /api/clinics
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClinic(@Valid @RequestBody Clinic clinic) {
        try {
            clinicService.saveClinic(clinic);
            return ResponseEntity.ok(clinic);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Actualizar clínica (solo ADMIN)
     * PUT /api/clinics/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateClinic(@PathVariable Long id,
            @Valid @RequestBody Clinic updatedClinic) {
        Clinic existingClinic = clinicService.findById(id);

        if (existingClinic == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Clínica no encontrada");
            return ResponseEntity.status(404).body(error);
        }

        // Actualizar campos
        existingClinic.setName(updatedClinic.getName());
        existingClinic.setAddress(updatedClinic.getAddress());
        existingClinic.setPhone(updatedClinic.getPhone());
        existingClinic.setEmail(updatedClinic.getEmail());
        existingClinic.setWebsite(updatedClinic.getWebsite());
        existingClinic.setOpeningHours(updatedClinic.getOpeningHours());
        existingClinic.setEmergency247(updatedClinic.isEmergency247());
        existingClinic.setDescription(updatedClinic.getDescription());
        existingClinic.setImageUrl(updatedClinic.getImageUrl());

        clinicService.saveClinic(existingClinic);

        return ResponseEntity.ok(existingClinic);
    }

    /**
     * Eliminar clínica (solo ADMIN)
     * DELETE /api/clinics/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteClinic(@PathVariable Long id) {
        Clinic clinic = clinicService.findById(id);

        if (clinic == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Clínica no encontrada");
            return ResponseEntity.status(404).body(error);
        }

        clinicService.deleteClinic(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Clínica eliminada exitosamente");
        return ResponseEntity.ok(response);
    }
}
