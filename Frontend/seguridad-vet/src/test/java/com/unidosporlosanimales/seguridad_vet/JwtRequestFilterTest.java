package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.security.CustomUserDetailsService;
import com.unidosporlosanimales.seguridad_vet.security.JwtRequestFilter;
import com.unidosporlosanimales.seguridad_vet.security.JwtUtil;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    private UserDetails userDetails(String username) {
        return new User(username, "pass", Collections.emptyList());
    }

    @Test
    void filtro_sinHeaderAuthorization_debePasarSinAutenticar() throws Exception {
        MockHttpServletRequest request   = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtRequestFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void filtro_headerSinPrefixBearer_debePasarSinAutenticar() throws Exception {
        MockHttpServletRequest request   = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Basic dXNlcjpwYXNz");

        jwtRequestFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void filtro_tokenValido_debeAutenticarUsuario() throws Exception {
        MockHttpServletRequest request   = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer tokenValido");

        UserDetails ud = userDetails("admin");
        when(jwtUtil.extractUsername("tokenValido")).thenReturn("admin");
        when(userDetailsService.loadUserByUsername("admin")).thenReturn(ud);
        when(jwtUtil.validateToken("tokenValido", ud)).thenReturn(true);

        jwtRequestFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("admin",
            SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void filtro_tokenInvalido_noDebeAutenticar() throws Exception {
        MockHttpServletRequest request   = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer tokenInvalido");

        UserDetails ud = userDetails("admin");
        when(jwtUtil.extractUsername("tokenInvalido")).thenReturn("admin");
        when(userDetailsService.loadUserByUsername("admin")).thenReturn(ud);
        when(jwtUtil.validateToken("tokenInvalido", ud)).thenReturn(false);

        jwtRequestFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void filtro_tokenMalformado_debePasarSinAutenticar() throws Exception {
        MockHttpServletRequest request   = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer tokenMalformado");

        when(jwtUtil.extractUsername("tokenMalformado"))
            .thenThrow(new RuntimeException("Token inválido"));

        jwtRequestFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
