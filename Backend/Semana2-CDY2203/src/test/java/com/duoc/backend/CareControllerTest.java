package com.duoc.backend;

import com.duoc.backend.care.Care;
import com.duoc.backend.care.CareController;
import com.duoc.backend.care.CareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CareControllerTest {

    // 1. AHORA SIMULAMOS EL REPOSITORIO (Ya que tu Controller lo usa directamente)
    @Mock
    private CareRepository careRepository;

    // 2. Lo inyectamos en el controlador real
    @InjectMocks
    private CareController careController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCares() {
        // Arrange
        when(careRepository.findAll()).thenReturn(Arrays.asList(new Care()));
        // Act
        Iterable<Care> result = careController.getAllCares();
        // Assert
        assertNotNull(result);
        verify(careRepository, times(1)).findAll();
    }

    @Test
    void testGetCareById() {
        // Arrange
        Care care = new Care();
        care.setId(1L);
        // El repositorio devuelve un Optional
        when(careRepository.findById(1L)).thenReturn(Optional.of(care));
        // Act
        Care result = careController.getCareById(1L);
        // Assert
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveCare() {
        // Arrange
        Care care = new Care();
        when(careRepository.save(care)).thenReturn(care);
        // Act
        Care result = careController.saveCare(care);
        // Assert
        assertNotNull(result);
    }

    @Test
    void testDeleteCare() {
        // Arrange
        doNothing().when(careRepository).deleteById(2L);
        // Act
        careController.deleteCare(2L);
        // Assert
        verify(careRepository, times(1)).deleteById(2L);
    }
}