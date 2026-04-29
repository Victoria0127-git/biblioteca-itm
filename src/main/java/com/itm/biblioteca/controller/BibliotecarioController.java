package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.service.IBibliotecarioService; // Usamos la interfaz
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecarios")
@RequiredArgsConstructor
public class BibliotecarioController {
    private final IBibliotecarioService bibliotecarioService; // Inyectamos el SERVICE, no el repo

    @GetMapping
    public List<Bibliotecario> obtenerTodos() {
        return bibliotecarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bibliotecario> obtenerPorId(@PathVariable String id) {
        return bibliotecarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearBibliotecario(@RequestBody Bibliotecario bibliotecario) {
        try {
            Bibliotecario guardado = bibliotecarioService.crear(bibliotecario);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            // Si el validador del Service lanza error, aquí lo atrapamos
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBibliotecario(@PathVariable String id, @RequestBody Bibliotecario bibliotecario) {
        try {
            Bibliotecario actualizado = bibliotecarioService.actualizar(id, bibliotecario);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBibliotecario(@PathVariable String id) {
        try {
            bibliotecarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}