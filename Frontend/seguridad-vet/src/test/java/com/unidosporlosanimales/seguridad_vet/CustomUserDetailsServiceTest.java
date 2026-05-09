package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.model.Usuario;
import com.unidosporlosanimales.seguridad_vet.repository.UsuarioRepository;
import com.unidosporlosanimales.seguridad_vet.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_usuarioExistente_debeRetornarUserDetails() {
        Usuario usuario = new Usuario("admin", "hashedPass", "ROLE_ADMIN");
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));

        UserDetails result = customUserDetailsService.loadUserByUsername("admin");

        assertNotNull(result);
        assertEquals("admin",      result.getUsername());
        assertEquals("hashedPass", result.getPassword());
        assertTrue(result.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_usuarioNoExistente_debeLanzarExcepcion() {
        when(usuarioRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername("noexiste"));
    }

    @Test
    void loadUserByUsername_debeConsultarRepositorioConUsernameExacto() {
        Usuario usuario = new Usuario("veterinario", "pass", "ROLE_VET");
        when(usuarioRepository.findByUsername("veterinario")).thenReturn(Optional.of(usuario));

        customUserDetailsService.loadUserByUsername("veterinario");

        verify(usuarioRepository, times(1)).findByUsername("veterinario");
    }

    @Test
    void loadUserByUsername_debeRetornarRolCorrecto() {
        Usuario usuario = new Usuario("recepcion", "pass", "ROLE_USER");
        when(usuarioRepository.findByUsername("recepcion")).thenReturn(Optional.of(usuario));

        UserDetails result = customUserDetailsService.loadUserByUsername("recepcion");

        assertEquals(1, result.getAuthorities().size());
        assertEquals("ROLE_USER",
            result.getAuthorities().iterator().next().getAuthority());
    }
}
