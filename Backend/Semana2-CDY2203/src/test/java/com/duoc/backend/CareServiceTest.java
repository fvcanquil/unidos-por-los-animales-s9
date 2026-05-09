package com.duoc.backend;

import com.duoc.backend.care.Care;
import com.duoc.backend.care.CareRepository;
import com.duoc.backend.care.CareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CareServiceTest {

    // 1. Simulamos la base de datos
    @Mock
    private CareRepository careRepository;

    // 2. Inyectamos la base de datos falsa en tu servicio real
    @InjectMocks
    private CareService careService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCares() {
        // Arrange
        when(careRepository.findAll()).thenReturn(Arrays.asList(new Care(), new Care()));
        // Act
        Iterable<Care> result = careService.getAllCares();
        // Assert
        assertNotNull(result);
        verify(careRepository, times(1)).findAll();
    }

    @Test
    void testGetCareById() {
        // Arrange
        Care care = new Care();
        care.setId(10L);
        when(careRepository.findById(10L)).thenReturn(Optional.of(care));
        // Act
        Care result = careService.getCareById(10L);
        // Assert
        assertEquals(10L, result.getId());
    }

    @Test
    void testSaveCare() {
        // Arrange
        Care care = new Care();
        care.setName("Vacuna");
        when(careRepository.save(any(Care.class))).thenReturn(care);
        // Act
        Care result = careService.saveCare(care);
        // Assert
        assertEquals("Vacuna", result.getName());
        verify(careRepository, times(1)).save(care);
    }

    @Test
    void testDeleteCare() {
        // Arrange
        doNothing().when(careRepository).deleteById(1L);
        // Act
        careService.deleteCare(1L);
        // Assert
        verify(careRepository, times(1)).deleteById(1L);
    }
}