package com.crud_trabajo.app.repository;

import com.crud_trabajo.app.model.Entrenador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntrenadorRepository extends MongoRepository<Entrenador, String> {
    List<Entrenador> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
}
