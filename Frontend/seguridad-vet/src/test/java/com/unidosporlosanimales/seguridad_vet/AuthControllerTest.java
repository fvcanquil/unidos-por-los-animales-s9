package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.controller.AuthController;
import com.unidosporlosanimales.seguridad_vet.security.CustomUserDetailsService;
import com.unidosporlosanimales.seguridad_vet.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private UserDetails userDetails(String username) {
        return new User(username, "encodedPass", Collections.emptyList());
    }

    @Test
    void login_credencialesValidas_debeRetornar200ConToken() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        req.setUsername("admin");
        req.setPassword("admin123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(null);
        when(userDetailsService.loadUserByUsername("admin"))
            .thenReturn(userDetails("admin"));
        when(jwtUtil.generateToken(any(UserDetails.class)))
            .thenReturn("token.jwt.generado");

        ResponseEntity<?> response = authController.createAuthenticationToken(req);

        assertEquals(200, response.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("token.jwt.generado", body.get("token"));
    }

    @Test
    void login_credencialesInvalidas_debeRetornar401() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        req.setUsername("admin");
        req.setPassword("claveIncorrecta");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        ResponseEntity<?> response = authController.createAuthenticationToken(req);

        assertEquals(401, response.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Usuario o contraseña incorrectos", body.get("error"));
    }

    @Test
    void login_exitoso_debeConsultarUserDetailsService() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        req.setUsername("veterinario");
        req.setPassword("vet123");

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userDetailsService.loadUserByUsername("veterinario"))
            .thenReturn(userDetails("veterinario"));
        when(jwtUtil.generateToken(any())).thenReturn("token");

        authController.createAuthenticationToken(req);

        verify(userDetailsService).loadUserByUsername("veterinario");
        verify(jwtUtil).generateToken(any(UserDetails.class));
    }

    @Test
    void login_fallido_noDebeConsultarUserDetailsService() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        req.setUsername("admin");
        req.setPassword("mal");

        when(authenticationManager.authenticate(any()))
            .thenThrow(new BadCredentialsException("bad credentials"));

        authController.createAuthenticationToken(req);

        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(jwtUtil, never()).generateToken(any());
    }
}
