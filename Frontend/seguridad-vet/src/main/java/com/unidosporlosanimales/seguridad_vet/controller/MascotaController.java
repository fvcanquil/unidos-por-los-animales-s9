package com.unidosporlosanimales.seguridad_vet.controller;

import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import com.unidosporlosanimales.seguridad_vet.repository.DuenoRepository;
import com.unidosporlosanimales.seguridad_vet.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * API REST para gestión de Mascotas.
 * Todas las rutas requieren JWT válido.
 *
 * Rutas:
 *   GET    /api/mascotas        → Listar todas las mascotas
 *   GET    /api/mascotas/{id}   → Obtener una mascota por ID
 *   POST   /api/mascotas        → Registrar nueva mascota
 *   DELETE /api/mascotas/{id}   → Eliminar una mascota
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private DuenoRepository duenoRepository;

    /**
     * GET /api/mascotas
     * Retorna un DTO simplificado para evitar referencias circulares JSON
     * (Mascota → Dueno → Mascotas → Mascota → ...)
     */
    @GetMapping
    public List<Map<String, Object>> listarMascotas() {
        List<Mascota> mascotas = mascotaRepository.findAll();
        return mascotas.stream().map(m -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", m.getId());
            dto.put("nombre", m.getNombre());
            dto.put("especie", m.getEspecie());
            dto.put("raza", m.getRaza());
            dto.put("edad", m.getEdad());
            dto.put("duenoId", m.getDueno().getId());
            dto.put("duenoNombre", m.getDueno().getNombre() + " " + m.getDueno().getApellido());
            return dto;
        }).toList();
    }

    /** GET /api/mascotas/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerMascota(@PathVariable Long id) {
        Optional<Mascota> opt = mascotaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Mascota m = opt.get();
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", m.getId());
        dto.put("nombre", m.getNombre());
        dto.put("especie", m.getEspecie());
        dto.put("raza", m.getRaza());
        dto.put("edad", m.getEdad());
        dto.put("duenoId", m.getDueno().getId());
        dto.put("duenoNombre", m.getDueno().getNombre() + " " + m.getDueno().getApellido());
        return ResponseEntity.ok(dto);
    }

    /**
     * POST /api/mascotas
     * Body esperado: { "nombre":"...", "especie":"...", "raza":"...", "edad":N, "duenoId":N }
     */
    @PostMapping
    public ResponseEntity<?> crearMascota(@RequestBody Map<String, Object> body) {
        Long duenoId = Long.valueOf(body.get("duenoId").toString());
        Optional<Dueno> dueno = duenoRepository.findById(duenoId);
        if (dueno.isEmpty()) {
            return ResponseEntity.badRequest().body("Dueño no encontrado con id: " + duenoId);
        }
        Mascota mascota = new Mascota(
            body.get("nombre").toString(),
            body.get("especie").toString(),
            body.get("raza").toString(),
            Integer.valueOf(body.get("edad").toString()),
            dueno.get()
        );
        Mascota guardada = mascotaRepository.save(mascota);
        Map<String, Object> response = new HashMap<>();
        response.put("id", guardada.getId());
        response.put("mensaje", "Mascota registrada exitosamente");
        return ResponseEntity.ok(response);
    }

    /** DELETE /api/mascotas/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        if (!mascotaRepository.existsById(id)) return ResponseEntity.notFound().build();
        mascotaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
