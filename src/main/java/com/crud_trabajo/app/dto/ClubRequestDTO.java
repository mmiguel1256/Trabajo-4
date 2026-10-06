package com.crud_trabajo.app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class ClubRequestDTO {

    @NotBlank(message = "El nombre del club es obligatorio")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    private String estadio;

    @Min(value = 1800, message = "El año de fundación debe ser válido")
    private int anioFundacion;

    private String pais;

    private String entrenadorId;

    private List<String> jugadorIds;

    public ClubRequestDTO() {
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

    public String getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(String entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public List<String> getJugadorIds() {
        return jugadorIds;
    }

    public void setJugadorIds(List<String> jugadorIds) {
        this.jugadorIds = jugadorIds;
    }
}
