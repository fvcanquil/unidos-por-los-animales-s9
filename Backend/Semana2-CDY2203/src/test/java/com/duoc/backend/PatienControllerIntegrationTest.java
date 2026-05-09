package com.duoc.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import com.duoc.backend.user.MyUserDetailsService;
import com.duoc.backend.user.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PatienControllerIntegrationTest {

    @LocalServerPort
    private int port;
    
    @Autowired
    private Environment environment;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    // INYECTAMOS EL MOCK
    @MockitoBean
    private MyUserDetailsService userDetailsService;

    @Test
    void testLoginAndGetAllPatients() {
        // Arrange
        // SIMULAMOS QUE EL USUARIO EXISTE
        UserDetails mockUser = org.springframework.security.core.userdetails.User.withUsername("prueba").password("123456").roles("USER").build();
        Mockito.when(userDetailsService.loadUserByUsername("prueba")).thenReturn(mockUser);

        String loginUrl = "http://localhost:" + port + "/login";
        String patientUrl = "http://localhost:" + port + "/patient";

        User loginRequest = new User();
        loginRequest.setUsername("prueba");
        loginRequest.setPassword("123456");

        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> loginRequestEntity = new HttpEntity<>(loginRequest, loginHeaders);

        // Act: Obtener Token
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginRequestEntity, String.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        
        String bearerToken = loginResponse.getBody(); 
        assertNotNull(bearerToken);

        // Usar el token para llamar al servicio /patient
        HttpHeaders patientHeaders = new HttpHeaders();
        patientHeaders.setContentType(MediaType.APPLICATION_JSON);
        patientHeaders.setBearerAuth(bearerToken.replace("Bearer ", "")); // Aseguramos formato limpio

        HttpEntity<String> patientRequest = new HttpEntity<>(patientHeaders);

        // Act: Llamar al servicio
        ResponseEntity<String> patientResponse = restTemplate.exchange(patientUrl, HttpMethod.GET, patientRequest, String.class);

        // Assert
        assertEquals(HttpStatus.OK, patientResponse.getStatusCode());
        assertNotNull(patientResponse.getBody());
    }

    @Test
    void testGetAllPatientsWithBearerToken() {
        String baseUrl = "http://localhost:"+ port + "/patient";
        String json = restTemplate.getForObject(baseUrl, String.class);
        assertNotNull(json);
    }

    @WithMockUser
    @Test
    void getMvc() throws Exception {
        this.mvc.perform(get("/patient"))
            .andExpect(status().isOk());
    }
}