package com.unidosporlosanimales.seguridad_vet.repository;

import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Mascota.
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Buscar todas las mascotas de un dueño específico
    List<Mascota> findByDuenoId(Long duenoId);
}
