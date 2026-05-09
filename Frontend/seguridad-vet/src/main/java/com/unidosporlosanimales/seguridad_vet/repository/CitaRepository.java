package com.unidosporlosanimales.seguridad_vet.repository;

import com.unidosporlosanimales.seguridad_vet.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Cita.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Buscar citas de una mascota específica
    List<Cita> findByMascotaId(Long mascotaId);
    // Buscar citas por estado
    List<Cita> findByEstado(String estado);
}
