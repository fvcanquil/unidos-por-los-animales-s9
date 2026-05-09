package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.controller.CitaController;
import com.unidosporlosanimales.seguridad_vet.model.Cita;
import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import com.unidosporlosanimales.seguridad_vet.repository.CitaRepository;
import com.unidosporlosanimales.seguridad_vet.repository.MascotaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private CitaController citaController;

    private Mascota mascotaDePrueba() {
        Dueno dueno = new Dueno("Carlos", "Rojas", "carlos@mail.com", "+569");
        dueno.setId(1L);
        Mascota m = new Mascota("Toby", "Perro", "Labrador", 3, dueno);
        m.setId(1L);
        return m;
    }

    private Cita citaDePrueba(Mascota mascota) {
        Cita c = new Cita(LocalDate.now().plusDays(2), "Control anual", "PENDIENTE", mascota);
        c.setId(1L);
        return c;
    }

    @Test
    void listarCitas_debeRetornarListaDTO() {
        Mascota mascota = mascotaDePrueba();
        Cita cita = citaDePrueba(mascota);
        when(citaRepository.findAll()).thenReturn(List.of(cita));

        List<Map<String, Object>> resultado = citaController.listarCitas();

        assertEquals(1, resultado.size());
        assertEquals("Control anual", resultado.get(0).get("motivo"));
        assertEquals("PENDIENTE",     resultado.get(0).get("estado"));
        assertEquals("Toby",          resultado.get(0).get("mascotaNombre"));
        verify(citaRepository).findAll();
    }

    @Test
    void listarCitas_listaVacia_debeRetornarVacia() {
        when(citaRepository.findAll()).thenReturn(List.of());
        assertTrue(citaController.listarCitas().isEmpty());
    }

    @Test
    void obtenerCita_idExistente_debeRetornar200() {
        Mascota mascota = mascotaDePrueba();
        Cita cita = citaDePrueba(mascota);
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        ResponseEntity<Map<String, Object>> response = citaController.obtenerCita(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Control anual", response.getBody().get("motivo"));
        assertEquals("Toby",          response.getBody().get("mascotaNombre"));
    }

    @Test
    void obtenerCita_idInexistente_debeRetornar404() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = citaController.obtenerCita(99L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void crearCita_mascotaExistente_debeRetornar200() {
        Mascota mascota = mascotaDePrueba();
        Cita guardada = citaDePrueba(mascota);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(citaRepository.save(any(Cita.class))).thenReturn(guardada);

        Map<String, Object> body = new HashMap<>();
        body.put("fecha",     LocalDate.now().plusDays(3).toString());
        body.put("motivo",    "Vacunación");
        body.put("mascotaId", 1);

        ResponseEntity<?> response = citaController.crearCita(body);

        assertEquals(200, response.getStatusCode().value());
        verify(citaRepository).save(any(Cita.class));
    }

    @Test
    void crearCita_mascotaInexistente_debeRetornar400() {
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        Map<String, Object> body = new HashMap<>();
        body.put("fecha",     LocalDate.now().toString());
        body.put("motivo",    "Emergencia");
        body.put("mascotaId", 99);

        ResponseEntity<?> response = citaController.crearCita(body);

        assertEquals(400, response.getStatusCode().value());
        verify(citaRepository, never()).save(any());
    }

    @Test
    void actualizarEstado_citaExistente_debeRetornar200() {
        Mascota mascota = mascotaDePrueba();
        Cita cita = citaDePrueba(mascota);
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Map<String, String> body = new HashMap<>();
        body.put("estado", "ATENDIDA");

        ResponseEntity<?> response = citaController.actualizarEstado(1L, body);

        assertEquals(200, response.getStatusCode().value());
        verify(citaRepository).save(cita);
    }

    @Test
    void actualizarEstado_citaInexistente_debeRetornar404() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = citaController.actualizarEstado(99L, Map.of("estado", "CANCELADA"));

        assertEquals(404, response.getStatusCode().value());
        verify(citaRepository, never()).save(any());
    }
}
