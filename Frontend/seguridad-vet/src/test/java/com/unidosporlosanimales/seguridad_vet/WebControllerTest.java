package com.unidosporlosanimales.seguridad_vet;

import com.unidosporlosanimales.seguridad_vet.controller.WebController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WebControllerTest {

    private final WebController webController = new WebController();

    @Test
    void home_debeRetornarVistaIndex() {
        assertEquals("index", webController.home());
    }

    @Test
    void login_debeRetornarVistaLogin() {
        assertEquals("login", webController.login());
    }

    @Test
    void dashboard_debeRetornarVistaDashboard() {
        assertEquals("dashboard", webController.dashboard());
    }
}
