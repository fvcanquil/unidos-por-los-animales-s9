package com.unidosporlosanimales.seguridad_vet.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que representa una cita veterinaria.
 * Cada cita está asociada a una mascota.
 */
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String motivo;    // Ej: "Control anual", "Vacunación", "Emergencia"

    @Column(nullable = false)
    private String estado;    // "PENDIENTE", "ATENDIDA", "CANCELADA"

    // Cada cita pertenece a una mascota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    public Cita() {}

    public Cita(LocalDate fecha, String motivo, String estado, Mascota mascota) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = estado;
        this.mascota = mascota;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
}
