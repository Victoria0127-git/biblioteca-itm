package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.service.IEditorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoriales")
@RequiredArgsConstructor
public class EditorialController {
    private final IEditorialService editorialService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    public List<Editorial> obtenerTodas() {
        return editorialService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editorial> obtenerPorId(@PathVariable String id) {
        return editorialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearEditorial(@RequestBody Editorial editorial) {
        try {
            Editorial guardada = editorialService.crear(editorial);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEditorial(@PathVariable String id, @RequestBody Editorial editorial) {
        try {
            Editorial actualizado = editorialService.actualizar(id, editorial);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEditorial(@PathVariable String id) {
        try {
            editorialService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}