package com.crud_trabajo.app.controller;

import com.crud_trabajo.app.model.Entrenador;
import com.crud_trabajo.app.service.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entrenadores")
@CrossOrigin(origins = "*")
public class EntrenadorRestController {

    private final EntrenadorService entrenadorService;

    public EntrenadorRestController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @GetMapping
    public ResponseEntity<List<Entrenador>> listarTodos() {
        return ResponseEntity.ok(entrenadorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> obtenerPorId(@PathVariable String id) {
        return entrenadorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entrenador> crear(@Valid @RequestBody Entrenador entrenador) {
        Entrenador nuevo = entrenadorService.save(entrenador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizar(@PathVariable String id, @Valid @RequestBody Entrenador entrenador) {
        Entrenador actualizado = entrenadorService.update(id, entrenador);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String id) {
        entrenadorService.deleteById(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("mensaje", "Entrenador eliminado exitosamente");
        resp.put("id", id);
        return ResponseEntity.ok(resp);
    }
}
