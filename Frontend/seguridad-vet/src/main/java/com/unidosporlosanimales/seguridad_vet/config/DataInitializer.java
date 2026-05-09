package com.unidosporlosanimales.seguridad_vet.config;

import com.unidosporlosanimales.seguridad_vet.model.*;
import com.unidosporlosanimales.seguridad_vet.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * Inicializador de datos de prueba.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UsuarioRepository usuarioRepository,
            DuenoRepository duenoRepository,
            MascotaRepository mascotaRepository,
            CitaRepository citaRepository) {

        return args -> {
            PasswordEncoder encoder = new BCryptPasswordEncoder();

            // =============================================
            // 1. USUARIOS DEL SISTEMA 
            // =============================================
            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario("admin",       encoder.encode("admin123"),  "ROLE_ADMIN"));
                usuarioRepository.save(new Usuario("veterinario", encoder.encode("vet123"),    "ROLE_VET"));
                usuarioRepository.save(new Usuario("recepcion",   encoder.encode("recep123"),  "ROLE_USER"));
                System.out.println("=== 3 Usuarios iniciales creados en la BD ===");
            }

            // =============================================
            // 2. DUEÑOS DE MASCOTAS
            // =============================================
            if (duenoRepository.count() == 0) {
                Dueno dueno1 = duenoRepository.save(
                    new Dueno("Carlos", "Rojas", "carlos.rojas@email.com", "+56912345678"));
                Dueno dueno2 = duenoRepository.save(
                    new Dueno("María", "González", "maria.gonzalez@email.com", "+56987654321"));
                Dueno dueno3 = duenoRepository.save(
                    new Dueno("Pedro", "Álvarez", "pedro.alvarez@email.com", "+56911223344"));

                System.out.println("=== 3 Dueños de mascotas creados en la BD ===");

                // =============================================
                // 3. MASCOTAS
                // =============================================
                Mascota mascota1 = mascotaRepository.save(
                    new Mascota("Toby",   "Perro", "Labrador",       3, dueno1));
                Mascota mascota2 = mascotaRepository.save(
                    new Mascota("Luna",   "Gato",  "Siamés",         2, dueno2));
                Mascota mascota3 = mascotaRepository.save(
                    new Mascota("Rocky",  "Perro", "Golden Retriever",5, dueno3));
                Mascota mascota4 = mascotaRepository.save(
                    new Mascota("Misi",   "Gato",  "Persa",           1, dueno1));

                System.out.println("=== 4 Mascotas creadas en la BD ===");

                // =============================================
                // 4. CITAS VETERINARIAS
                // =============================================
                citaRepository.save(new Cita(LocalDate.now().plusDays(2),  "Control anual",  "PENDIENTE",  mascota1));
                citaRepository.save(new Cita(LocalDate.now().plusDays(5),  "Vacunación",     "PENDIENTE",  mascota2));
                citaRepository.save(new Cita(LocalDate.now().minusDays(3), "Emergencia",     "ATENDIDA",   mascota3));
                citaRepository.save(new Cita(LocalDate.now().plusDays(10), "Castración",     "PENDIENTE",  mascota4));
                citaRepository.save(new Cita(LocalDate.now().minusDays(7), "Desparasitación","ATENDIDA",   mascota1));

                System.out.println("=== 5 Citas veterinarias creadas en la BD ===");
            }
        };
    }
}
