package com.duoc.backend;

import com.duoc.backend.user.User;
import com.duoc.backend.user.UserRepository;
import com.duoc.backend.user.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Arrange: Creamos un usuario de prueba
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Act: Llamamos al método
        UserDetails result = myUserDetailsService.loadUserByUsername("testuser");

        // Assert: Verificamos los datos cargados
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        // Arrange: Simulamos que el repositorio no encuentra al usuario
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        // Act & Assert: Verificamos que lance la excepción correcta
        assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername("unknown");
        });
    }

    @Test
    void testPasswordEncoderBean() {
        // Verificamos que el bean del codificador no sea nulo
        PasswordEncoder encoder = myUserDetailsService.passwordEncoder();
        assertNotNull(encoder);
    }
}