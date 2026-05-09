package com.duoc.backend;

import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Invoice.InvoiceController;
import com.duoc.backend.Invoice.InvoiceService;
import com.duoc.backend.care.Care;
import com.duoc.backend.medication.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class InvoiceControllerTest {

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInvoices() {
        when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(new Invoice()));
        List<Invoice> result = invoiceController.getAllInvoices();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetInvoiceById() {
        Invoice inv = new Invoice();
        inv.setId(1L);
        when(invoiceService.getInvoiceById(1L)).thenReturn(inv);
        
        Invoice result = invoiceController.getInvoiceById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveInvoice() {
        Invoice inv = new Invoice();
        when(invoiceService.saveInvoice(any(Invoice.class))).thenReturn(inv);
        
        Invoice result = invoiceController.saveInvoice(inv);
        assertNotNull(result);
    }

    @Test
    void testDeleteInvoice() {
        doNothing().when(invoiceService).deleteInvoice(1L);
        invoiceController.deleteInvoice(1L);
        verify(invoiceService, times(1)).deleteInvoice(1L);
    }

    @Test
    void testGenerateInvoicePdfSuccess() {
        // Arrange: Datos completos para entrar a los ciclos FOR
        Invoice inv = new Invoice();
        inv.setId(1L);
        inv.setPatientName("Mascota Test");
        inv.setDate(LocalDate.now());
        inv.setTotalCost(5000.0);
        
        Care c = new Care(); c.setName("Vacuna"); c.setCost(2000.0);
        Medication m = new Medication(); m.setName("Jarabe"); m.setCost(3000.0);
        
        inv.setCares(Arrays.asList(c));
        inv.setMedications(Arrays.asList(m));

        when(invoiceService.getInvoiceById(1L)).thenReturn(inv);

        // Act
        ResponseEntity<byte[]> response = invoiceController.generateInvoicePdf(1L);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGenerateInvoicePdfNotFound() {
        // Arrange: El servicio devuelve null
        when(invoiceService.getInvoiceById(1L)).thenReturn(null);

        // Act
        ResponseEntity<byte[]> response = invoiceController.generateInvoicePdf(1L);

        // Assert
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testGenerateInvoicePdfException() {
        // Arrange: Creamos una factura que causará error DENTRO del try
        // Al dejar 'cares' como null, el ciclo for (Care care : invoice.getCares()) lanzará NullPointerException
        Invoice inv = new Invoice();
        inv.setId(1L);
        inv.setPatientName("Error Test");
        inv.setCares(null); 

        when(invoiceService.getInvoiceById(1L)).thenReturn(inv);

        // Act
        ResponseEntity<byte[]> response = invoiceController.generateInvoicePdf(1L);

        // Assert: El catch lo atrapa y devuelve 500
        assertEquals(500, response.getStatusCode().value());
    }
}