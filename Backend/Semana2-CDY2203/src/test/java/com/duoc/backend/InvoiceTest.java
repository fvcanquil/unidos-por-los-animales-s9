package com.duoc.backend;

import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.care.Care;
import com.duoc.backend.medication.Medication;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void testInvoiceGettersAndSetters() {
        // Arrange: Preparamos los datos falsos
        Invoice invoice = new Invoice();
        LocalDate testDate = LocalDate.of(2026, 4, 29);
        LocalTime testTime = LocalTime.of(15, 30);
        List<Care> testCares = new ArrayList<>();
        List<Medication> testMedications = new ArrayList<>();

        // Act: Usamos todos los Setters
        invoice.setId(10L);
        invoice.setPatientName("Boby");
        invoice.setDate(testDate);
        invoice.setTime(testTime);
        invoice.setCares(testCares);
        invoice.setMedications(testMedications);
        invoice.setTotalCost(45000.0);

        // Assert: Verificamos con todos los Getters
        assertEquals(10L, invoice.getId());
        assertEquals("Boby", invoice.getPatientName());
        assertEquals(testDate, invoice.getDate());
        assertEquals(testTime, invoice.getTime());
        assertEquals(testCares, invoice.getCares());
        assertEquals(testMedications, invoice.getMedications());
        assertEquals(45000.0, invoice.getTotalCost());
    }
}