package com.unidosporlosanimales.seguridad_vet.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa a una mascota registrada en la veterinaria.
 * Cada mascota pertenece a un dueño (relación ManyToOne).
 */
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especie;   // Ej: "Perro", "Gato", "Conejo"

    @Column(nullable = false)
    private String raza;

    @Column(nullable = false)
    private Integer edad;     // Edad en años

    // Cada mascota pertenece a un dueño (relación ManyToOne)
    // @JsonIgnore no es necesario aquí — usaremos DTOs para evitar referencias circulares
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dueno_id", nullable = false)
    private Dueno dueno;

    // Una mascota puede tener muchas citas
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;

    public Mascota() {}

    public Mascota(String nombre, String especie, String raza, Integer edad, Dueno dueno) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.dueno = dueno;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public Dueno getDueno() { return dueno; }
    public void setDueno(Dueno dueno) { this.dueno = dueno; }
    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
}
