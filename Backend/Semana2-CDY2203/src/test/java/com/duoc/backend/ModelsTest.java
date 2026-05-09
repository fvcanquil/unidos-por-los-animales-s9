package com.duoc.backend;

import com.duoc.backend.patient.Patient;
import com.duoc.backend.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelsTest {

    @Test
    void testPatientModel() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");
        patient.setSpecies("Perro");
        patient.setBreed("Labrador");
        patient.setAge(5);
        patient.setOwner("Juan");

        assertEquals(1L, patient.getId());
        assertEquals("Firulais", patient.getName());
        assertEquals("Perro", patient.getSpecies());
        assertEquals("Labrador", patient.getBreed());
        assertEquals(5, patient.getAge());
        assertEquals("Juan", patient.getOwner());
    }

    @Test
    void testUserModel() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        // Si tu clase User tiene un atributo de "token", descomenta lo siguiente:
        // user.setToken("mi_token");
        // assertEquals("mi_token", user.getToken());

        assertEquals("admin", user.getUsername());
        assertEquals("123456", user.getPassword());
    }
}