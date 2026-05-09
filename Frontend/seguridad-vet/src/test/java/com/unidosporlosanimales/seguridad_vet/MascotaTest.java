package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.model.Cita;
import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MascotaTest {

    private Dueno duenoDePrueba() {
        return new Dueno("Carlos", "Rojas", "carlos@email.com", "+56912345678");
    }

    @Test
    void constructorConParametros_debeAsignarValoresCorrectamente() {
        Dueno dueno = duenoDePrueba();
        Mascota mascota = new Mascota("Toby", "Perro", "Labrador", 3, dueno);

        assertEquals("Toby",    mascota.getNombre());
        assertEquals("Perro",   mascota.getEspecie());
        assertEquals("Labrador",mascota.getRaza());
        assertEquals(3,         mascota.getEdad());
        assertEquals(dueno,     mascota.getDueno());
    }

    @Test
    void constructorVacio_debeCrearObjetoSinExcepcion() {
        Mascota mascota = new Mascota();
        assertNotNull(mascota);
    }

    @Test
    void setters_debenActualizarTodosLosValores() {
        Dueno dueno = duenoDePrueba();
        Mascota mascota = new Mascota();
        mascota.setId(5L);
        mascota.setNombre("Luna");
        mascota.setEspecie("Gato");
        mascota.setRaza("Siamés");
        mascota.setEdad(2);
        mascota.setDueno(dueno);

        assertEquals(5L,      mascota.getId());
        assertEquals("Luna",  mascota.getNombre());
        assertEquals("Gato",  mascota.getEspecie());
        assertEquals("Siamés",mascota.getRaza());
        assertEquals(2,       mascota.getEdad());
        assertEquals(dueno,   mascota.getDueno());
    }

    @Test
    void setCitas_debeAsignarListaCorrectamente() {
        Mascota mascota = new Mascota("Rocky", "Perro", "Golden", 5, duenoDePrueba());
        Cita cita = new Cita();
        cita.setMotivo("Control anual");
        mascota.setCitas(List.of(cita));

        assertNotNull(mascota.getCitas());
        assertEquals(1, mascota.getCitas().size());
        assertEquals("Control anual", mascota.getCitas().get(0).getMotivo());
    }

    @Test
    void setEdad_debeActualizarEdadCorrectamente() {
        Mascota mascota = new Mascota("Misi", "Gato", "Persa", 1, duenoDePrueba());
        mascota.setEdad(4);
        assertEquals(4, mascota.getEdad());
    }
}
