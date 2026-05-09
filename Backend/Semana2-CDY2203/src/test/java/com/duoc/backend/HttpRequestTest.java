package com.duoc.backend;

import com.duoc.backend.user.User;
import com.duoc.backend.user.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // 1. INYECTAMOS EL SERVICIO DE USUARIOS SIMULADO
    @MockitoBean
    private MyUserDetailsService userDetailsService;

    @Test
    void testLoginSuccess() {
        // Arrange
        // 2. SIMULAMOS QUE EL USUARIO EXISTE EN LA BASE DE DATOS
        UserDetails mockUser = org.springframework.security.core.userdetails.User.withUsername("prueba").password("123456").roles("USER").build();
        Mockito.when(userDetailsService.loadUserByUsername("prueba")).thenReturn(mockUser);

        String baseUrl = "http://localhost:" + port + "/login";
        User loginRequest = new User();
        loginRequest.setUsername("prueba");
        loginRequest.setPassword("123456");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(loginRequest, headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().startsWith("Bearer "));
    }

    @Test
    void testLoginInvalidPassword() {
        // Arrange
        // SIMULAMOS EL USUARIO CON SU CLAVE REAL (123456)
        UserDetails mockUser = org.springframework.security.core.userdetails.User.withUsername("prueba").password("123456").roles("USER").build();
        Mockito.when(userDetailsService.loadUserByUsername("prueba")).thenReturn(mockUser);

        String baseUrl = "http://localhost:" + port + "/login";
        User loginRequest = new User();
        loginRequest.setUsername("prueba");
        loginRequest.setPassword("1234567"); // CLAVE INCORRECTA ENVIADA

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(loginRequest, headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);

        // Assert
        // Como el controlador lanza RuntimeException, Spring devuelve 500 Internal Server Error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}