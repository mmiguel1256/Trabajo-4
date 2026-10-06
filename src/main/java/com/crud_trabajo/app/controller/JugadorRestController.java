package com.crud_trabajo.app.controller;

import com.crud_trabajo.app.model.Jugador;
import com.crud_trabajo.app.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jugadores")
@CrossOrigin(origins = "*")
public class JugadorRestController {

    private final JugadorService jugadorService;

    public JugadorRestController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> listarTodos() {
        return ResponseEntity.ok(jugadorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerPorId(@PathVariable String id) {
        return jugadorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<Jugador>> listarPorClub(@PathVariable String clubId) {
        return ResponseEntity.ok(jugadorService.findByIdClub(clubId));
    }

    @PostMapping
    public ResponseEntity<Jugador> crear(@Valid @RequestBody Jugador jugador) {
        Jugador nuevo = jugadorService.save(jugador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable String id, @Valid @RequestBody Jugador jugador) {
        Jugador actualizado = jugadorService.update(id, jugador);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String id) {
        jugadorService.deleteById(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("mensaje", "Jugador eliminado exitosamente");
        resp.put("id", id);
        return ResponseEntity.ok(resp);
    }
}
