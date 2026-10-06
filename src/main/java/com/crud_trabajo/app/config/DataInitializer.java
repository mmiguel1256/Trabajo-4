package com.crud_trabajo.app.config;

import com.crud_trabajo.app.model.Club;
import com.crud_trabajo.app.model.Entrenador;
import com.crud_trabajo.app.model.Jugador;
import com.crud_trabajo.app.repository.ClubRepository;
import com.crud_trabajo.app.repository.EntrenadorRepository;
import com.crud_trabajo.app.repository.JugadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final ClubRepository clubRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final JugadorRepository jugadorRepository;

    public DataInitializer(ClubRepository clubRepository,
                           EntrenadorRepository entrenadorRepository,
                           JugadorRepository jugadorRepository) {
        this.clubRepository = clubRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Override
    public void run(String... args) {
        long clubCount = clubRepository.count();
        log.info("Verificando datos iniciales en MongoDB Atlas... Total de clubes actuales: {}", clubCount);

        if (clubCount < 4) {
            log.info("Inicializando datos de prueba (4 Clubes, sus Entrenadores y Jugadores)...");

            // Limpiar datos previos para garantizar consistencia si hay menos de 4
            if (clubCount == 0) {
                jugadorRepository.deleteAll();
                entrenadorRepository.deleteAll();
            }

            // ==========================================
            // 1. REAL MADRID CF
            // ==========================================
            Entrenador ancelotti = entrenadorRepository.save(
                    new Entrenador("Carlo", "Ancelotti", 65, "Italiano", 30)
            );

            Club realMadrid = new Club("Real Madrid CF", "Madrid", "Estadio Santiago Bernabéu", 1902, "España");
            realMadrid.setEntrenador(ancelotti);
            realMadrid = clubRepository.save(realMadrid);

            List<Jugador> jugadoresRM = Arrays.asList(
                    new Jugador("Vinícius", "Júnior", 7, "Delantero", 24, "Brasileño"),
                    new Jugador("Kylian", "Mbappé", 9, "Delantero", 25, "Francés"),
                    new Jugador("Jude", "Bellingham", 5, "Centrocampista", 21, "Inglés"),
                    new Jugador("Federico", "Valverde", 8, "Centrocampista", 26, "Uruguayo"),
                    new Jugador("Antonio", "Rüdiger", 22, "Defensa", 31, "Alemán"),
                    new Jugador("Thibaut", "Courtois", 1, "Portero", 32, "Belga")
            );

            for (Jugador j : jugadoresRM) {
                j.setIdClub(realMadrid.getId());
                j.setNombreClub(realMadrid.getNombre());
                Jugador saved = jugadorRepository.save(j);
                realMadrid.agregarJugador(saved);
            }
            clubRepository.save(realMadrid);

            // ==========================================
            // 2. FC BARCELONA
            // ==========================================
            Entrenador flick = entrenadorRepository.save(
                    new Entrenador("Hansi", "Flick", 59, "Alemán", 22)
            );

            Club barcelona = new Club("FC Barcelona", "Barcelona", "Spotify Camp Nou", 1899, "España");
            barcelona.setEntrenador(flick);
            barcelona = clubRepository.save(barcelona);

            List<Jugador> jugadoresBarca = Arrays.asList(
                    new Jugador("Robert", "Lewandowski", 9, "Delantero", 36, "Polaco"),
                    new Jugador("Lamine", "Yamal", 19, "Delantero", 17, "Español"),
                    new Jugador("Pedro", "González (Pedri)", 8, "Centrocampista", 22, "Español"),
                    new Jugador("Pablo", "Páez (Gavi)", 6, "Centrocampista", 20, "Español"),
                    new Jugador("Pau", "Cubarsí", 2, "Defensa", 17, "Español"),
                    new Jugador("Marc-André", "ter Stegen", 1, "Portero", 32, "Alemán")
            );

            for (Jugador j : jugadoresBarca) {
                j.setIdClub(barcelona.getId());
                j.setNombreClub(barcelona.getNombre());
                Jugador saved = jugadorRepository.save(j);
                barcelona.agregarJugador(saved);
            }
            clubRepository.save(barcelona);

            // ==========================================
            // 3. MANCHESTER CITY FC
            // ==========================================
            Entrenador guardiola = entrenadorRepository.save(
                    new Entrenador("Pep", "Guardiola", 53, "Español", 17)
            );

            Club manCity = new Club("Manchester City FC", "Mánchester", "Etihad Stadium", 1880, "Inglaterra");
            manCity.setEntrenador(guardiola);
            manCity = clubRepository.save(manCity);

            List<Jugador> jugadoresCity = Arrays.asList(
                    new Jugador("Erling", "Haaland", 9, "Delantero", 24, "Noruego"),
                    new Jugador("Kevin", "De Bruyne", 17, "Centrocampista", 33, "Belga"),
                    new Jugador("Phil", "Foden", 47, "Delantero", 24, "Inglés"),
                    new Jugador("Rodrigo", "Hernández (Rodri)", 16, "Centrocampista", 28, "Español"),
                    new Jugador("Rúben", "Dias", 3, "Defensa", 27, "Portugués"),
                    new Jugador("Ederson", "Moraes", 31, "Portero", 31, "Brasileño")
            );

            for (Jugador j : jugadoresCity) {
                j.setIdClub(manCity.getId());
                j.setNombreClub(manCity.getNombre());
                Jugador saved = jugadorRepository.save(j);
                manCity.agregarJugador(saved);
            }
            clubRepository.save(manCity);

            // ==========================================
            // 4. PARIS SAINT-GERMAIN FC
            // ==========================================
            Entrenador luisEnrique = entrenadorRepository.save(
                    new Entrenador("Luis", "Enrique", 54, "Español", 19)
            );

            Club psg = new Club("Paris Saint-Germain FC", "París", "Parc des Princes", 1970, "Francia");
            psg.setEntrenador(luisEnrique);
            psg = clubRepository.save(psg);

            List<Jugador> jugadoresPsg = Arrays.asList(
                    new Jugador("Ousmane", "Dembélé", 10, "Delantero", 27, "Francés"),
                    new Jugador("Bradley", "Barcola", 29, "Delantero", 22, "Francés"),
                    new Jugador("Vítor", "Machado (Vitinha)", 17, "Centrocampista", 24, "Portugués"),
                    new Jugador("Achraf", "Hakimi", 2, "Defensa", 26, "Marroquí"),
                    new Jugador("Marcos", "Aoás (Marquinhos)", 5, "Defensa", 30, "Brasileño"),
                    new Jugador("Gianluigi", "Donnarumma", 1, "Portero", 25, "Italiano")
            );

            for (Jugador j : jugadoresPsg) {
                j.setIdClub(psg.getId());
                j.setNombreClub(psg.getNombre());
                Jugador saved = jugadorRepository.save(j);
                psg.agregarJugador(saved);
            }
            clubRepository.save(psg);

            log.info("Base de datos MongoDB Atlas inicializada con éxito con los 4 clubes, entrenadores y planteles.");
        } else {
            log.info("La base de datos ya contiene {} clubes. Omitiendo seed inicial.", clubCount);
        }
    }
}
