package com.duoc.backend;

import com.duoc.backend.medication.Medication;
import com.duoc.backend.medication.MedicationController;
import com.duoc.backend.medication.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicationControllerTest {

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMedications() {
        when(medicationService.getAllMedications()).thenReturn(Arrays.asList(new Medication()));
        List<Medication> result = medicationController.getAllMedications();
        assertFalse(result.isEmpty());
        verify(medicationService, times(1)).getAllMedications();
    }

    @Test
    void testGetMedicationById() {
        Medication med = new Medication();
        med.setId(1L);
        when(medicationService.getMedicationById(1L)).thenReturn(med);
        
        Medication result = medicationController.getMedicationById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveMedication() {
        Medication med = new Medication();
        when(medicationService.saveMedication(any(Medication.class))).thenReturn(med);
        
        Medication result = medicationController.saveMedication(med);
        assertNotNull(result);
        verify(medicationService, times(1)).saveMedication(med);
    }

    @Test
    void testDeleteMedication() {
        doNothing().when(medicationService).deleteMedication(1L);
        medicationController.deleteMedication(1L);
        verify(medicationService, times(1)).deleteMedication(1L);
    }
}