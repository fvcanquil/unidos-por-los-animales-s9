package com.unidosporlosanimales.seguridad_vet.controller;

import com.unidosporlosanimales.seguridad_vet.model.Cita;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import com.unidosporlosanimales.seguridad_vet.repository.CitaRepository;
import com.unidosporlosanimales.seguridad_vet.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * API REST para gestión de Citas veterinarias.
 * Todas las rutas requieren JWT válido.
 *
 * Rutas:
 *   GET  /api/citas              → Listar todas las citas
 *   GET  /api/citas/{id}         → Obtener cita por ID
 *   POST /api/citas              → Agendar nueva cita
 *   PUT  /api/citas/{id}/estado  → Actualizar estado de una cita
 */
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    /** GET /api/citas — Lista todas las citas con datos de la mascota asociada */
    @GetMapping
    public List<Map<String, Object>> listarCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream().map(c -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", c.getId());
            dto.put("fecha", c.getFecha().toString());
            dto.put("motivo", c.getMotivo());
            dto.put("estado", c.getEstado());
            dto.put("mascotaId", c.getMascota().getId());
            dto.put("mascotaNombre", c.getMascota().getNombre());
            dto.put("mascotaEspecie", c.getMascota().getEspecie());
            return dto;
        }).toList();
    }

    /** GET /api/citas/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCita(@PathVariable Long id) {
        Optional<Cita> opt = citaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Cita c = opt.get();
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", c.getId());
        dto.put("fecha", c.getFecha().toString());
        dto.put("motivo", c.getMotivo());
        dto.put("estado", c.getEstado());
        dto.put("mascotaId", c.getMascota().getId());
        dto.put("mascotaNombre", c.getMascota().getNombre());
        return ResponseEntity.ok(dto);
    }

    /**
     * POST /api/citas — Agendar nueva cita
     * Body: { "fecha":"YYYY-MM-DD", "motivo":"...", "mascotaId":N }
     */
    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Map<String, Object> body) {
        Long mascotaId = Long.valueOf(body.get("mascotaId").toString());
        Optional<Mascota> mascota = mascotaRepository.findById(mascotaId);
        if (mascota.isEmpty()) {
            return ResponseEntity.badRequest().body("Mascota no encontrada con id: " + mascotaId);
        }
        Cita cita = new Cita(
            LocalDate.parse(body.get("fecha").toString()),
            body.get("motivo").toString(),
            "PENDIENTE",  // Estado inicial siempre es PENDIENTE
            mascota.get()
        );
        Cita guardada = citaRepository.save(cita);
        Map<String, Object> response = new HashMap<>();
        response.put("id", guardada.getId());
        response.put("mensaje", "Cita agendada exitosamente");
        return ResponseEntity.ok(response);
    }

    /** PUT /api/citas/{id}/estado — Cambiar estado de la cita */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Cita> opt = citaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Cita cita = opt.get();
        cita.setEstado(body.get("estado")); // "ATENDIDA" o "CANCELADA"
        citaRepository.save(cita);
        return ResponseEntity.ok(Map.of("mensaje", "Estado actualizado a: " + body.get("estado")));
    }
}
