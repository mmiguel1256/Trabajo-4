package com.crud_trabajo.app.repository;

import com.crud_trabajo.app.model.Club;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends MongoRepository<Club, String> {
    Optional<Club> findByNombreIgnoreCase(String nombre);
    List<Club> findByNombreContainingIgnoreCase(String nombre);
    List<Club> findByCiudadContainingIgnoreCase(String ciudad);
}
