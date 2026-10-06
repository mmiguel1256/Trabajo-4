package com.crud_trabajo.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "clubes")
public class Club {

    @Id
    private String id;

    @NotBlank(message = "El nombre del club es requerido")
    private String nombre;

    @NotBlank(message = "La ciudad es requerida")
    private String ciudad;

    private String estadio;

    @Min(value = 1800, message = "El año de fundación debe ser válido")
    private int anioFundacion;

    private String pais;

    // Relación OneToOne representada en MongoDB mediante @DocumentReference (Slide 1, 3 y 8)
    @DocumentReference
    private Entrenador entrenador;

    // Relación OneToMany representada en MongoDB mediante @DocumentReference (Slide 1, 3 y 8)
    @DocumentReference
    private List<Jugador> jugadores = new ArrayList<>();

    public Club() {
    }

    public Club(String id, String nombre, String ciudad, String estadio, int anioFundacion, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.anioFundacion = anioFundacion;
        this.pais = pais;
    }

    public Club(String nombre, String ciudad, String estadio, int anioFundacion, String pais) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.anioFundacion = anioFundacion;
        this.pais = pais;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public int getAnioFundacion() {
        return anioFundacion;
    }

    public void setAnioFundacion(int anioFundacion) {
        this.anioFundacion = anioFundacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Jugador> getJugadores() {
        if (this.jugadores == null) {
            this.jugadores = new ArrayList<>();
        }
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores != null ? jugadores : new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        if (this.jugadores == null) {
            this.jugadores = new ArrayList<>();
        }
        if (jugador != null && !this.jugadores.contains(jugador)) {
            this.jugadores.add(jugador);
        }
    }

    public void removerJugador(Jugador jugador) {
        if (this.jugadores != null && jugador != null) {
            this.jugadores.remove(jugador);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return Objects.equals(id, club.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
