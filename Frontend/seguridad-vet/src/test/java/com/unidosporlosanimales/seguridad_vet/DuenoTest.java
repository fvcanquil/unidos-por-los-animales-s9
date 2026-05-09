package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DuenoTest {

    @Test
    void constructorConParametros_debeAsignarValoresCorrectamente() {
        Dueno dueno = new Dueno("Carlos", "Rojas", "carlos@email.com", "+56912345678");

        assertEquals("Carlos",          dueno.getNombre());
        assertEquals("Rojas",           dueno.getApellido());
        assertEquals("carlos@email.com",dueno.getEmail());
        assertEquals("+56912345678",    dueno.getTelefono());
    }

    @Test
    void constructorVacio_debeCrearObjetoSinExcepcion() {
        Dueno dueno = new Dueno();
        assertNotNull(dueno);
    }

    @Test
    void setters_debenActualizarTodosLosValores() {
        Dueno dueno = new Dueno();
        dueno.setId(10L);
        dueno.setNombre("María");
        dueno.setApellido("González");
        dueno.setEmail("maria@email.com");
        dueno.setTelefono("+56987654321");

        assertEquals(10L,               dueno.getId());
        assertEquals("María",           dueno.getNombre());
        assertEquals("González",        dueno.getApellido());
        assertEquals("maria@email.com", dueno.getEmail());
        assertEquals("+56987654321",    dueno.getTelefono());
    }

    @Test
    void setMascotas_debeAsignarListaCorrectamente() {
        Dueno dueno = new Dueno("Pedro", "Álvarez", "pedro@email.com", "+56911223344");
        Mascota m = new Mascota();
        m.setNombre("Toby");
        dueno.setMascotas(List.of(m));

        assertNotNull(dueno.getMascotas());
        assertEquals(1, dueno.getMascotas().size());
        assertEquals("Toby", dueno.getMascotas().get(0).getNombre());
    }

    @Test
    void getMascotas_sinAsignar_debeRetornarNull() {
        Dueno dueno = new Dueno("A", "B", "a@b.com", "123");
        assertNull(dueno.getMascotas());
    }
}
