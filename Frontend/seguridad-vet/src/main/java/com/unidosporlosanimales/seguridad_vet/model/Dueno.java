package com.unidosporlosanimales.seguridad_vet.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa al dueño de una mascota.
 * Hibernate generará automáticamente la tabla "duenos" en MySQL.
 */
@Entity
@Table(name = "duenos")
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefono;

    // Un dueño puede tener muchas mascotas
    // cascade = REMOVE: si se elimina el dueño, se eliminan sus mascotas
    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mascota> mascotas;

    public Dueno() {}

    public Dueno(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<Mascota> getMascotas() { return mascotas; }
    public void setMascotas(List<Mascota> mascotas) { this.mascotas = mascotas; }
}
