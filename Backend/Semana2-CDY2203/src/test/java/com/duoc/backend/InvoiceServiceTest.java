package com.duoc.backend;

import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Invoice.InvoiceRepository;
import com.duoc.backend.Invoice.InvoiceService;
import com.duoc.backend.care.Care;
import com.duoc.backend.care.CareRepository;
import com.duoc.backend.medication.Medication;
import com.duoc.backend.medication.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveInvoiceCalculatesTotalCostCorrectly() {
        // Arrange
        Care care = new Care(); 
        care.setId(1L); 
        care.setCost(20000.0);

        Medication med = new Medication(); 
        med.setId(1L); 
        med.setCost(15000.0);

        Invoice invoice = new Invoice();
        invoice.setCares(Arrays.asList(care));
        invoice.setMedications(Arrays.asList(med));

        // Simulamos que los repositorios encuentran los objetos por sus IDs
        when(careRepository.findAllById(anyIterable())).thenReturn(Arrays.asList(care));
        when(medicationRepository.findAllById(anyIterable())).thenReturn(Arrays.asList(med));
        
        // Al guardar, devolvemos el mismo objeto para verificar el cálculo del totalCost
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Invoice result = invoiceService.saveInvoice(invoice);

        // Assert
        assertNotNull(result);
        assertEquals(35000.0, result.getTotalCost(), "El costo total debe ser la suma de care (20000) y medication (15000)");
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    void testGetAllInvoices() {
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(new Invoice(), new Invoice()));
        Iterable<Invoice> result = invoiceService.getAllInvoices();
        assertNotNull(result);
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void testGetInvoiceById() {
        Invoice inv = new Invoice();
        inv.setId(5L);
        when(invoiceRepository.findById(5L)).thenReturn(Optional.of(inv));
        
        Invoice result = invoiceService.getInvoiceById(5L);
        assertNotNull(result);
        assertEquals(5L, result.getId());
    }

    @Test
    void testDeleteInvoice() {
        doNothing().when(invoiceRepository).deleteById(1L);
        invoiceService.deleteInvoice(1L);
        verify(invoiceRepository, times(1)).deleteById(1L);
    }
}