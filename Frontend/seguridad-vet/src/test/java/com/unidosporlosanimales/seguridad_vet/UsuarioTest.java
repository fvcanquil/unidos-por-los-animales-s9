package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.model.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void constructorConParametros_debeAsignarValoresCorrectamente() {
        Usuario usuario = new Usuario("admin", "pass123", "ROLE_ADMIN");

        assertEquals("admin",      usuario.getUsername());
        assertEquals("pass123",    usuario.getPassword());
        assertEquals("ROLE_ADMIN", usuario.getRol());
    }

    @Test
    void constructorVacio_debeCrearObjetoSinNulos() {
        Usuario usuario = new Usuario();
        assertNotNull(usuario);
    }

    @Test
    void setters_debenActualizarValoresCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("veterinario");
        usuario.setPassword("vet123");
        usuario.setRol("ROLE_VET");

        assertEquals(1L,            usuario.getId());
        assertEquals("veterinario", usuario.getUsername());
        assertEquals("vet123",      usuario.getPassword());
        assertEquals("ROLE_VET",    usuario.getRol());
    }

    @Test
    void setUsername_debeActualizarUsername() {
        Usuario usuario = new Usuario("original", "pass", "ROLE_USER");
        usuario.setUsername("nuevo");
        assertEquals("nuevo", usuario.getUsername());
    }

    @Test
    void setRol_debeActualizarRol() {
        Usuario usuario = new Usuario("u", "p", "ROLE_USER");
        usuario.setRol("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", usuario.getRol());
    }
}
