package com.crud_trabajo.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Document(collection = "entrenadores")
public class Entrenador {

    @Id
    private String id;

    @NotBlank(message = "El nombre del entrenador es requerido")
    private String nombre;

    @NotBlank(message = "El apellido del entrenador es requerido")
    private String apellido;

    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private int edad;

    @NotBlank(message = "La nacionalidad es requerida")
    private String nacionalidad;

    private int aniosExperiencia;

    public Entrenador() {
    }

    public Entrenador(String id, String nombre, String apellido, int edad, String nacionalidad, int aniosExperiencia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.aniosExperiencia = aniosExperiencia;
    }

    public Entrenador(String nombre, String apellido, int edad, String nacionalidad, int aniosExperiencia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
