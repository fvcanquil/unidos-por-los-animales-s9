package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    // Clave de al menos 256 bits requerida por HS256
    private static final String SECRET =
        "ClaveSuperSecretaParaFirmarLosTokensDeLaVeterinaria2024MuyLarga";
    private static final long EXPIRATION = 86400000L; // 24 horas

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret",     SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expiration", EXPIRATION);
    }

    private UserDetails userDetails(String username) {
        return new User(username, "password", Collections.emptyList());
    }

    @Test
    void generateToken_debeRetornarTokenNoNulo() {
        String token = jwtUtil.generateToken(userDetails("admin"));
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void extractUsername_debeRetornarUsernameCorrectamente() {
        UserDetails ud = userDetails("veterinario");
        String token = jwtUtil.generateToken(ud);

        assertEquals("veterinario", jwtUtil.extractUsername(token));
    }

    @Test
    void validateToken_conTokenValido_debeRetornarTrue() {
        UserDetails ud = userDetails("recepcion");
        String token = jwtUtil.generateToken(ud);

        assertTrue(jwtUtil.validateToken(token, ud));
    }

    @Test
    void validateToken_conUsernameDistinto_debeRetornarFalse() {
        String token = jwtUtil.generateToken(userDetails("admin"));
        assertFalse(jwtUtil.validateToken(token, userDetails("otro")));
    }

    @Test
    void extractExpiration_debeRetornarFechaFutura() {
        String token = jwtUtil.generateToken(userDetails("admin"));
        assertTrue(jwtUtil.extractExpiration(token).after(new java.util.Date()));
    }

    @Test
    void generateToken_diferentesUsuarios_debenProducirTokensDistintos() {
        String token1 = jwtUtil.generateToken(userDetails("admin"));
        String token2 = jwtUtil.generateToken(userDetails("veterinario"));
        assertNotEquals(token1, token2);
    }
}
