package com.duoc.backend;

import com.duoc.backend.medication.Medication;
import com.duoc.backend.medication.MedicationRepository;
import com.duoc.backend.medication.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMedications() {
        when(medicationRepository.findAll()).thenReturn(Arrays.asList(new Medication(), new Medication()));
        List<Medication> result = medicationService.getAllMedications();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    void testGetMedicationById() {
        Medication med = new Medication();
        med.setId(1L);
        when(medicationRepository.findById(1L)).thenReturn(Optional.of(med));
        
        Medication result = medicationService.getMedicationById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveMedication() {
        Medication med = new Medication();
        med.setName("Paracetamol");
        when(medicationRepository.save(any(Medication.class))).thenReturn(med);
        
        Medication result = medicationService.saveMedication(med);
        assertEquals("Paracetamol", result.getName());
        verify(medicationRepository, times(1)).save(med);
    }

    @Test
    void testDeleteMedication() {
        doNothing().when(medicationRepository).deleteById(1L);
        medicationService.deleteMedication(1L);
        verify(medicationRepository, times(1)).deleteById(1L);
    }
}