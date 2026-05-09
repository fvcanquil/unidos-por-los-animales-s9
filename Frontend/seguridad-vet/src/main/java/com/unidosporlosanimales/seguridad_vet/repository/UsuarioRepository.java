package com.unidosporlosanimales.seguridad_vet.repository;

import com.unidosporlosanimales.seguridad_vet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Este método es crucial para que Spring Security busque al usuario al hacer login
    Optional<Usuario> findByUsername(String username);
}