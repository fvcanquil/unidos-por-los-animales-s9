package com.unidosporlosanimales.seguridad_vet.repository;

import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Dueno.
 * Spring Data JPA implementa automáticamente los métodos CRUD.
 */
@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long> {
}
