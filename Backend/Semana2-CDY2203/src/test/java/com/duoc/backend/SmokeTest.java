package com.duoc.backend;

import static org.assertj.core.api.Assertions.assertThat;

import com.duoc.backend.patient.PatientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SmokeTest {

    @Autowired
    private PatientController patientController;

    @Autowired
    private LoginController loginController;

    @Test
    void contextLoads() throws Exception {
        assertThat(patientController).isNotNull();
        assertThat(loginController).isNotNull();
    }
}