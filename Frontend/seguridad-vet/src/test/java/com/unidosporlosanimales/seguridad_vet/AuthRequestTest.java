package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.controller.AuthController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void setUsername_debeActualizarUsername() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        req.setUsername("admin");
        assertEquals("admin", req.getUsername());
    }

    @Test
    void setPassword_debeActualizarPassword() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        req.setPassword("admin123");
        assertEquals("admin123", req.getPassword());
    }

    @Test
    void getters_sinSetters_debenRetornarNull() {
        AuthController.AuthRequest req = new AuthController.AuthRequest();
        assertNull(req.getUsername());
        assertNull(req.getPassword());
    }
}
