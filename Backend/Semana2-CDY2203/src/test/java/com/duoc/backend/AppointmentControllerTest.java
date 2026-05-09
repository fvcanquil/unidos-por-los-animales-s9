package com.duoc.backend;

import com.duoc.backend.appointment.Appointment;
import com.duoc.backend.appointment.AppointmentController;
import com.duoc.backend.appointment.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAppointments() {
        when(appointmentService.getAllAppointments()).thenReturn(Arrays.asList(new Appointment()));
        List<Appointment> result = appointmentController.getAllAppointments();
        assertFalse(result.isEmpty());
        verify(appointmentService, times(1)).getAllAppointments();
    }

    @Test
    void testGetAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentService.getAppointmentById(1L)).thenReturn(appointment);
        
        Appointment result = appointmentController.getAppointmentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentService.saveAppointment(any(Appointment.class))).thenReturn(appointment);
        
        Appointment result = appointmentController.saveAppointment(appointment);
        assertNotNull(result);
        verify(appointmentService, times(1)).saveAppointment(appointment);
    }

    @Test
    void testDeleteAppointment() {
        doNothing().when(appointmentService).deleteAppointment(1L);
        appointmentController.deleteAppointment(1L);
        verify(appointmentService, times(1)).deleteAppointment(1L);
    }
}