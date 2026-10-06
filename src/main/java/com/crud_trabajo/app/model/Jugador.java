package com.crud_trabajo.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Document(collection = "jugadores")
public class Jugador {

    @Id
    private String id;

    @NotBlank(message = "El nombre del jugador es requerido")
    private String nombre;

    @NotBlank(message = "El apellido del jugador es requerido")
    private String apellido;

    @Min(value = 1, message = "El dorsal mínimo es 1")
    @Max(value = 99, message = "El dorsal máximo es 99")
    private int numero;

    @NotBlank(message = "La posición es requerida")
    private String posicion;

    private int edad;

    private String nacionalidad;

    // Relación conceptual id_club (JoinColumn según diapositiva)
    private String idClub;
    private String nombreClub;

    public Jugador() {
    }

    public Jugador(String id, String nombre, String apellido, int numero, String posicion, int edad, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.posicion = posicion;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
    }

    public Jugador(String nombre, String apellido, int numero, String posicion, int edad, String nacionalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.posicion = posicion;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
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

    public String getIdClub() {
        return idClub;
    }

    public void setIdClub(String idClub) {
        this.idClub = idClub;
    }

    public String getNombreClub() {
        return nombreClub;
    }

    public void setNombreClub(String nombreClub) {
        this.nombreClub = nombreClub;
    }

    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
