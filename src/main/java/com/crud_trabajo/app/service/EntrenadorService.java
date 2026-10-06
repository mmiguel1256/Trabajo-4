package com.crud_trabajo.app.service;

import com.crud_trabajo.app.model.Club;
import com.crud_trabajo.app.model.Entrenador;
import com.crud_trabajo.app.repository.ClubRepository;
import com.crud_trabajo.app.repository.EntrenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final ClubRepository clubRepository;

    public EntrenadorService(EntrenadorRepository entrenadorRepository, ClubRepository clubRepository) {
        this.entrenadorRepository = entrenadorRepository;
        this.clubRepository = clubRepository;
    }

    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    public Optional<Entrenador> findById(String id) {
        return entrenadorRepository.findById(id);
    }

    public Entrenador save(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador update(String id, Entrenador updated) {
        return entrenadorRepository.findById(id).map(existing -> {
            existing.setNombre(updated.getNombre());
            existing.setApellido(updated.getApellido());
            existing.setEdad(updated.getEdad());
            existing.setNacionalidad(updated.getNacionalidad());
            existing.setAniosExperiencia(updated.getAniosExperiencia());
            return entrenadorRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Entrenador no encontrado con ID: " + id));
    }

    public void deleteById(String id) {
        // Desvincular de cualquier club que tenga asignado este entrenador
        List<Club> clubes = clubRepository.findAll();
        for (Club c : clubes) {
            if (c.getEntrenador() != null && id.equals(c.getEntrenador().getId())) {
                c.setEntrenador(null);
                clubRepository.save(c);
            }
        }
        entrenadorRepository.deleteById(id);
    }
}
