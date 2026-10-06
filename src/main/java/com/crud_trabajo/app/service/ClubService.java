package com.crud_trabajo.app.service;

import com.crud_trabajo.app.dto.ClubRequestDTO;
import com.crud_trabajo.app.model.Club;
import com.crud_trabajo.app.model.Entrenador;
import com.crud_trabajo.app.model.Jugador;
import com.crud_trabajo.app.repository.ClubRepository;
import com.crud_trabajo.app.repository.EntrenadorRepository;
import com.crud_trabajo.app.repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final JugadorRepository jugadorRepository;

    public ClubService(ClubRepository clubRepository,
                       EntrenadorRepository entrenadorRepository,
                       JugadorRepository jugadorRepository) {
        this.clubRepository = clubRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.jugadorRepository = jugadorRepository;
    }

    public List<Club> findAll() {
        return clubRepository.findAll();
    }

    public Optional<Club> findById(String id) {
        return clubRepository.findById(id);
    }

    public Club save(ClubRequestDTO dto) {
        Club club = new Club();
        club.setNombre(dto.getNombre());
        club.setCiudad(dto.getCiudad());
        club.setEstadio(dto.getEstadio());
        club.setAnioFundacion(dto.getAnioFundacion());
        club.setPais(dto.getPais());

        if (dto.getEntrenadorId() != null && !dto.getEntrenadorId().isBlank()) {
            entrenadorRepository.findById(dto.getEntrenadorId()).ifPresent(club::setEntrenador);
        }

        List<Jugador> jugadores = new ArrayList<>();
        if (dto.getJugadorIds() != null && !dto.getJugadorIds().isEmpty()) {
            for (String jId : dto.getJugadorIds()) {
                jugadorRepository.findById(jId).ifPresent(jugadores::add);
            }
        }
        club.setJugadores(jugadores);

        Club guardado = clubRepository.save(club);

        // Actualizar referencia inversa en los jugadores
        for (Jugador j : jugadores) {
            j.setIdClub(guardado.getId());
            j.setNombreClub(guardado.getNombre());
            jugadorRepository.save(j);
        }

        return guardado;
    }

    public Club update(String id, ClubRequestDTO dto) {
        return clubRepository.findById(id).map(club -> {
            club.setNombre(dto.getNombre());
            club.setCiudad(dto.getCiudad());
            club.setEstadio(dto.getEstadio());
            club.setAnioFundacion(dto.getAnioFundacion());
            club.setPais(dto.getPais());

            // Actualizar Entrenador
            if (dto.getEntrenadorId() != null && !dto.getEntrenadorId().isBlank()) {
                entrenadorRepository.findById(dto.getEntrenadorId()).ifPresent(club::setEntrenador);
            } else {
                club.setEntrenador(null);
            }

            // Actualizar Jugadores
            if (dto.getJugadorIds() != null) {
                // Desvincular antiguos que ya no estén
                List<Jugador> antiguos = club.getJugadores();
                for (Jugador ant : antiguos) {
                    if (ant != null && !dto.getJugadorIds().contains(ant.getId())) {
                        ant.setIdClub(null);
                        ant.setNombreClub(null);
                        jugadorRepository.save(ant);
                    }
                }

                List<Jugador> nuevosJugadores = new ArrayList<>();
                for (String jId : dto.getJugadorIds()) {
                    jugadorRepository.findById(jId).ifPresent(j -> {
                        j.setIdClub(club.getId());
                        j.setNombreClub(club.getNombre());
                        jugadorRepository.save(j);
                        nuevosJugadores.add(j);
                    });
                }
                club.setJugadores(nuevosJugadores);
            }

            return clubRepository.save(club);
        }).orElseThrow(() -> new RuntimeException("Club no encontrado con ID: " + id));
    }

    public void deleteById(String id) {
        clubRepository.findById(id).ifPresent(club -> {
            // Desvincular a los jugadores para que no apunten a un club eliminado
            if (club.getJugadores() != null) {
                for (Jugador j : club.getJugadores()) {
                    if (j != null) {
                        j.setIdClub(null);
                        j.setNombreClub(null);
                        jugadorRepository.save(j);
                    }
                }
            }
            clubRepository.deleteById(id);
        });
    }

    public Club asignarEntrenador(String clubId, String entrenadorId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + clubId));
        Entrenador entrenador = entrenadorRepository.findById(entrenadorId)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado: " + entrenadorId));

        club.setEntrenador(entrenador);
        return clubRepository.save(club);
    }

    public Club desvincularEntrenador(String clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + clubId));
        club.setEntrenador(null);
        return clubRepository.save(club);
    }

    public Club agregarJugador(String clubId, String jugadorId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + clubId));
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado: " + jugadorId));

        // Si ya pertenecía a otro club, removerlo de allí
        if (jugador.getIdClub() != null && !jugador.getIdClub().isBlank() && !jugador.getIdClub().equals(clubId)) {
            clubRepository.findById(jugador.getIdClub()).ifPresent(c -> {
                c.removerJugador(jugador);
                clubRepository.save(c);
            });
        }

        jugador.setIdClub(club.getId());
        jugador.setNombreClub(club.getNombre());
        jugadorRepository.save(jugador);

        club.agregarJugador(jugador);
        return clubRepository.save(club);
    }

    public Club removerJugador(String clubId, String jugadorId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + clubId));
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado: " + jugadorId));

        jugador.setIdClub(null);
        jugador.setNombreClub(null);
        jugadorRepository.save(jugador);

        club.removerJugador(jugador);
        return clubRepository.save(club);
    }
}
