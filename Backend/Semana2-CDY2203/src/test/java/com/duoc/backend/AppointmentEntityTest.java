package com.duoc.backend;

import com.duoc.backend.appointment.Appointment;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentEntityTest {

    @Test
    void testAppointmentGettersAndSetters() {
        Appointment appointment = new Appointment();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        appointment.setId(1L);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setReason("Urgencia");
        appointment.setVeterinarian("Dr. Smith");

        assertEquals(1L, appointment.getId());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals("Urgencia", appointment.getReason());
        assertEquals("Dr. Smith", appointment.getVeterinarian());
    }
}