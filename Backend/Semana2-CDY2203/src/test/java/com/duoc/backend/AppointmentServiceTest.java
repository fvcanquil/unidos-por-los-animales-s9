package com.duoc.backend;

import com.duoc.backend.appointment.Appointment;
import com.duoc.backend.appointment.AppointmentRepository;
import com.duoc.backend.appointment.AppointmentService;
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

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(new Appointment(), new Appointment()));
        Iterable<Appointment> result = appointmentService.getAllAppointments();
        assertNotNull(result);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        
        Appointment result = appointmentService.getAppointmentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveAppointment() {
        Appointment appointment = new Appointment();
        appointment.setReason("Control");
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        
        Appointment result = appointmentService.saveAppointment(appointment);
        assertEquals("Control", result.getReason());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testDeleteAppointment() {
        doNothing().when(appointmentRepository).deleteById(1L);
        appointmentService.deleteAppointment(1L);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}