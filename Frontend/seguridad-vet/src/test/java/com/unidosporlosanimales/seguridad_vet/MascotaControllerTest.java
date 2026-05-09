package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.controller.MascotaController;
import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.model.Mascota;
import com.unidosporlosanimales.seguridad_vet.repository.DuenoRepository;
import com.unidosporlosanimales.seguridad_vet.repository.MascotaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaControllerTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private DuenoRepository duenoRepository;

    @InjectMocks
    private MascotaController mascotaController;

    private Dueno duenoDePrueba() {
        Dueno d = new Dueno("Carlos", "Rojas", "carlos@mail.com", "+569");
        d.setId(1L);
        return d;
    }

    private Mascota mascotaDePrueba(Dueno dueno) {
        Mascota m = new Mascota("Toby", "Perro", "Labrador", 3, dueno);
        m.setId(1L);
        return m;
    }

    @Test
    void listarMascotas_debeRetornarListaDTO() {
        Dueno dueno = duenoDePrueba();
        Mascota mascota = mascotaDePrueba(dueno);
        when(mascotaRepository.findAll()).thenReturn(List.of(mascota));

        List<Map<String, Object>> resultado = mascotaController.listarMascotas();

        assertEquals(1, resultado.size());
        assertEquals("Toby",  resultado.get(0).get("nombre"));
        assertEquals("Perro", resultado.get(0).get("especie"));
        assertEquals(1L,      resultado.get(0).get("duenoId"));
        verify(mascotaRepository).findAll();
    }

    @Test
    void listarMascotas_listaVacia_debeRetornarListaVacia() {
        when(mascotaRepository.findAll()).thenReturn(List.of());
        List<Map<String, Object>> resultado = mascotaController.listarMascotas();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerMascota_idExistente_debeRetornar200ConDTO() {
        Dueno dueno = duenoDePrueba();
        Mascota mascota = mascotaDePrueba(dueno);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        ResponseEntity<Map<String, Object>> response = mascotaController.obtenerMascota(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Toby", response.getBody().get("nombre"));
        assertEquals("Carlos Rojas", response.getBody().get("duenoNombre"));
    }

    @Test
    void obtenerMascota_idInexistente_debeRetornar404() {
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = mascotaController.obtenerMascota(99L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void crearMascota_duenoExistente_debeRetornar200() {
        Dueno dueno = duenoDePrueba();
        Mascota guardada = mascotaDePrueba(dueno);
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(dueno));
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(guardada);

        Map<String, Object> body = new HashMap<>();
        body.put("nombre",  "Toby");
        body.put("especie", "Perro");
        body.put("raza",    "Labrador");
        body.put("edad",    3);
        body.put("duenoId", 1);

        ResponseEntity<?> response = mascotaController.crearMascota(body);

        assertEquals(200, response.getStatusCode().value());
        verify(mascotaRepository).save(any(Mascota.class));
    }

    @Test
    void crearMascota_duenoInexistente_debeRetornar400() {
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());

        Map<String, Object> body = new HashMap<>();
        body.put("nombre",  "Luna");
        body.put("especie", "Gato");
        body.put("raza",    "Siamés");
        body.put("edad",    2);
        body.put("duenoId", 99);

        ResponseEntity<?> response = mascotaController.crearMascota(body);

        assertEquals(400, response.getStatusCode().value());
        verify(mascotaRepository, never()).save(any());
    }

    @Test
    void eliminarMascota_idExistente_debeRetornar204() {
        when(mascotaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(mascotaRepository).deleteById(1L);

        ResponseEntity<Void> response = mascotaController.eliminarMascota(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(mascotaRepository).deleteById(1L);
    }

    @Test
    void eliminarMascota_idInexistente_debeRetornar404() {
        when(mascotaRepository.existsById(99L)).thenReturn(false);

        ResponseEntity<Void> response = mascotaController.eliminarMascota(99L);

        assertEquals(404, response.getStatusCode().value());
        verify(mascotaRepository, never()).deleteById(any());
    }
}
