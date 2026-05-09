package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.model.Cita;
import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CitaTest {

    private Mascota mascotaDePrueba() {
        Dueno dueno = new Dueno("Carlos", "Rojas", "carlos@email.com", "+569");
        return new Mascota("Toby", "Perro", "Labrador", 3, dueno);
    }

    @Test
    void constructorConParametros_debeAsignarValoresCorrectamente() {
        LocalDate fecha = LocalDate.of(2025, 6, 15);
        Mascota mascota = mascotaDePrueba();
        Cita cita = new Cita(fecha, "Vacunación", "PENDIENTE", mascota);

        assertEquals(fecha,        cita.getFecha());
        assertEquals("Vacunación", cita.getMotivo());
        assertEquals("PENDIENTE",  cita.getEstado());
        assertEquals(mascota,      cita.getMascota());
    }

    @Test
    void constructorVacio_debeCrearObjetoSinExcepcion() {
        Cita cita = new Cita();
        assertNotNull(cita);
    }

    @Test
    void setters_debenActualizarTodosLosValores() {
        Mascota mascota = mascotaDePrueba();
        LocalDate fecha = LocalDate.now().plusDays(5);
        Cita cita = new Cita();

        cita.setId(1L);
        cita.setFecha(fecha);
        cita.setMotivo("Emergencia");
        cita.setEstado("ATENDIDA");
        cita.setMascota(mascota);

        assertEquals(1L,          cita.getId());
        assertEquals(fecha,       cita.getFecha());
        assertEquals("Emergencia",cita.getMotivo());
        assertEquals("ATENDIDA",  cita.getEstado());
        assertEquals(mascota,     cita.getMascota());
    }

    @Test
    void setEstado_debeActualizarEstado() {
        Cita cita = new Cita(LocalDate.now(), "Control", "PENDIENTE", mascotaDePrueba());
        cita.setEstado("CANCELADA");
        assertEquals("CANCELADA", cita.getEstado());
    }

    @Test
    void setFecha_debeActualizarFecha() {
        Cita cita = new Cita();
        LocalDate nuevaFecha = LocalDate.of(2026, 1, 10);
        cita.setFecha(nuevaFecha);
        assertEquals(nuevaFecha, cita.getFecha());
    }
}
