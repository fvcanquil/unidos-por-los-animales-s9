package com.unidosporlosanimales.seguridad_vet.controller;

import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * API REST para gestión de Dueños.
 *
 * SEGURIDAD: Todas las rutas bajo /api/** requieren JWT válido en el
 * header "Authorization: Bearer <token>" según lo configurado en WebSecurityConfig.
 *
 * Rutas disponibles:
 *   GET    /api/duenos          → Listar todos los dueños
 *   GET    /api/duenos/{id}     → Obtener un dueño por ID
 *   POST   /api/duenos          → Registrar nuevo dueño
 *   DELETE /api/duenos/{id}     → Eliminar un dueño
 */
@RestController
@RequestMapping("/api/duenos")
public class DuenoController {

    @Autowired
    private DuenoRepository duenoRepository;

    /** GET /api/duenos — Lista completa de dueños desde MySQL */
    @GetMapping
    public List<Dueno> listarDuenos() {
        return duenoRepository.findAll();
    }

    /** GET /api/duenos/{id} — Buscar un dueño por su ID */
    @GetMapping("/{id}")
    public ResponseEntity<Dueno> obtenerDueno(@PathVariable Long id) {
        Optional<Dueno> dueno = duenoRepository.findById(id);
        return dueno.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/duenos — Registrar un nuevo dueño */
    @PostMapping
    public Dueno crearDueno(@RequestBody Dueno dueno) {
        return duenoRepository.save(dueno);
    }

    /** DELETE /api/duenos/{id} — Eliminar un dueño */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDueno(@PathVariable Long id) {
        if (!duenoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        duenoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
