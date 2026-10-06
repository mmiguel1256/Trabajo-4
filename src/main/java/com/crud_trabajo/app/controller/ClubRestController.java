package com.crud_trabajo.app.controller;

import com.crud_trabajo.app.dto.ClubRequestDTO;
import com.crud_trabajo.app.model.Club;
import com.crud_trabajo.app.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clubes")
@CrossOrigin(origins = "*")
public class ClubRestController {

    private final ClubService clubService;

    public ClubRestController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<Club>> listarTodos() {
        return ResponseEntity.ok(clubService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> obtenerPorId(@PathVariable String id) {
        return clubService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Club> crear(@Valid @RequestBody ClubRequestDTO dto) {
        Club nuevo = clubService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Club> actualizar(@PathVariable String id, @Valid @RequestBody ClubRequestDTO dto) {
        Club actualizado = clubService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String id) {
        clubService.deleteById(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("mensaje", "Club eliminado exitosamente");
        resp.put("id", id);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{clubId}/entrenador/{entrenadorId}")
    public ResponseEntity<Club> asignarEntrenador(@PathVariable String clubId, @PathVariable String entrenadorId) {
        return ResponseEntity.ok(clubService.asignarEntrenador(clubId, entrenadorId));
    }

    @DeleteMapping("/{clubId}/entrenador")
    public ResponseEntity<Club> desvincularEntrenador(@PathVariable String clubId) {
        return ResponseEntity.ok(clubService.desvincularEntrenador(clubId));
    }

    @PostMapping("/{clubId}/jugadores/{jugadorId}")
    public ResponseEntity<Club> agregarJugador(@PathVariable String clubId, @PathVariable String jugadorId) {
        return ResponseEntity.ok(clubService.agregarJugador(clubId, jugadorId));
    }

    @DeleteMapping("/{clubId}/jugadores/{jugadorId}")
    public ResponseEntity<Club> removerJugador(@PathVariable String clubId, @PathVariable String jugadorId) {
        return ResponseEntity.ok(clubService.removerJugador(clubId, jugadorId));
    }
}
