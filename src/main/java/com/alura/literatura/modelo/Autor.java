package com.alura.literatura.modelo;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private int fechaNacimiento;
    private int fechaDefuncion;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(int fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(e->e.setAutor(this));
        this.libros = libros;
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDefuncion = datosAutor.fechaDefuncion();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
    }

    public Autor() {
    }

    @Override
    public String toString() {
        String librosTitulos = getLibros().stream()
                .map(libro -> "\"" + libro.getTitulo() + "\"")
                .collect(Collectors.joining(", "));

        String autorEncontrado = "------- AUTOR -------\n" +
                "Nombre: " + nombre + "\n" +
                "Nacimiento: " + fechaNacimiento + "\n" +
                "Defunción: " + fechaDefuncion + "\n" +
                "Libros: " + librosTitulos + "\n";

        return autorEncontrado;
    }
}
