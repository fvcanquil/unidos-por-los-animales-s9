package com.duoc.backend;

import com.duoc.backend.medication.Medication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicationEntityTest {

    @Test
    void testMedicationGettersAndSetters() {
        Medication med = new Medication();
        med.setId(1L);
        med.setName("Antibiótico");
        med.setCost(12500.0);

        assertEquals(1L, med.getId());
        assertEquals("Antibiótico", med.getName());
        assertEquals(12500.0, med.getCost());
    }
}