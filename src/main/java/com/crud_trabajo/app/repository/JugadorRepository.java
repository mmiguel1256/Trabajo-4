package com.crud_trabajo.app.repository;

import com.crud_trabajo.app.model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JugadorRepository extends MongoRepository<Jugador, String> {
    List<Jugador> findByIdClub(String idClub);
    List<Jugador> findByPosicion(String posicion);
    List<Jugador> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
}
