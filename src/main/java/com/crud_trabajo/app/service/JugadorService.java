package com.crud_trabajo.app.service;

import com.crud_trabajo.app.model.Club;
import com.crud_trabajo.app.model.Jugador;
import com.crud_trabajo.app.repository.ClubRepository;
import com.crud_trabajo.app.repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final ClubRepository clubRepository;

    public JugadorService(JugadorRepository jugadorRepository, ClubRepository clubRepository) {
        this.jugadorRepository = jugadorRepository;
        this.clubRepository = clubRepository;
    }

    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> findById(String id) {
        return jugadorRepository.findById(id);
    }

    public List<Jugador> findByIdClub(String idClub) {
        return jugadorRepository.findByIdClub(idClub);
    }

    public Jugador save(Jugador jugador) {
        // Si tiene idClub asignado, vincularlo al Club
        Jugador guardado = jugadorRepository.save(jugador);
        if (guardado.getIdClub() != null && !guardado.getIdClub().isBlank()) {
            clubRepository.findById(guardado.getIdClub()).ifPresent(club -> {
                guardado.setNombreClub(club.getNombre());
                jugadorRepository.save(guardado);
                club.agregarJugador(guardado);
                clubRepository.save(club);
            });
        }
        return guardado;
    }

    public Jugador update(String id, Jugador updated) {
        return jugadorRepository.findById(id).map(existing -> {
            String oldClubId = existing.getIdClub();
            existing.setNombre(updated.getNombre());
            existing.setApellido(updated.getApellido());
            existing.setNumero(updated.getNumero());
            existing.setPosicion(updated.getPosicion());
            existing.setEdad(updated.getEdad());
            existing.setNacionalidad(updated.getNacionalidad());

            String newClubId = updated.getIdClub();
            // Si cambió de club
            if (newClubId == null || newClubId.isBlank()) {
                if (oldClubId != null && !oldClubId.isBlank()) {
                    clubRepository.findById(oldClubId).ifPresent(c -> {
                        c.removerJugador(existing);
                        clubRepository.save(c);
                    });
                }
                existing.setIdClub(null);
                existing.setNombreClub(null);
            } else if (!newClubId.equals(oldClubId)) {
                // Remover del viejo
                if (oldClubId != null && !oldClubId.isBlank()) {
                    clubRepository.findById(oldClubId).ifPresent(c -> {
                        c.removerJugador(existing);
                        clubRepository.save(c);
                    });
                }
                // Agregar al nuevo
                clubRepository.findById(newClubId).ifPresent(newClub -> {
                    existing.setIdClub(newClub.getId());
                    existing.setNombreClub(newClub.getNombre());
                    newClub.agregarJugador(existing);
                    clubRepository.save(newClub);
                });
            }

            return jugadorRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID: " + id));
    }

    public void deleteById(String id) {
        // Remover de la lista de jugadores de cualquier club
        List<Club> clubes = clubRepository.findAll();
        for (Club c : clubes) {
            boolean removed = c.getJugadores().removeIf(j -> j != null && id.equals(j.getId()));
            if (removed) {
                clubRepository.save(c);
            }
        }
        jugadorRepository.deleteById(id);
    }
}
