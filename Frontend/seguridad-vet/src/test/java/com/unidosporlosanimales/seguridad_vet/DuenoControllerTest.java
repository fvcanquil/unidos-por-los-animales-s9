package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.controller.DuenoController;
import com.unidosporlosanimales.seguridad_vet.model.Dueno;
import com.unidosporlosanimales.seguridad_vet.repository.DuenoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuenoControllerTest {

    @Mock
    private DuenoRepository duenoRepository;

    @InjectMocks
    private DuenoController duenoController;

    @Test
    void listarDuenos_debeRetornarListaCompleta() {
        List<Dueno> lista = List.of(
            new Dueno("Carlos", "Rojas",    "carlos@mail.com", "+569"),
            new Dueno("María",  "González", "maria@mail.com",  "+568")
        );
        when(duenoRepository.findAll()).thenReturn(lista);

        List<Dueno> resultado = duenoController.listarDuenos();

        assertEquals(2, resultado.size());
        verify(duenoRepository).findAll();
    }

    @Test
    void obtenerDueno_idExistente_debeRetornar200() {
        Dueno dueno = new Dueno("Carlos", "Rojas", "carlos@mail.com", "+569");
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(dueno));

        ResponseEntity<Dueno> response = duenoController.obtenerDueno(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Carlos", response.getBody().getNombre());
    }

    @Test
    void obtenerDueno_idInexistente_debeRetornar404() {
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Dueno> response = duenoController.obtenerDueno(99L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void crearDueno_debeGuardarYRetornarDueno() {
        Dueno nuevo  = new Dueno("Pedro", "Álvarez", "pedro@mail.com", "+567");
        Dueno guardado = new Dueno("Pedro", "Álvarez", "pedro@mail.com", "+567");
        guardado.setId(3L);
        when(duenoRepository.save(nuevo)).thenReturn(guardado);

        Dueno resultado = duenoController.crearDueno(nuevo);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        verify(duenoRepository).save(nuevo);
    }

    @Test
    void eliminarDueno_idExistente_debeRetornar204() {
        when(duenoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(duenoRepository).deleteById(1L);

        ResponseEntity<Void> response = duenoController.eliminarDueno(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(duenoRepository).deleteById(1L);
    }

    @Test
    void eliminarDueno_idInexistente_debeRetornar404() {
        when(duenoRepository.existsById(99L)).thenReturn(false);

        ResponseEntity<Void> response = duenoController.eliminarDueno(99L);

        assertEquals(404, response.getStatusCode().value());
        verify(duenoRepository, never()).deleteById(any());
    }
}
