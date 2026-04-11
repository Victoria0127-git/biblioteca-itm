package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.service.IBibliotecarioService; // Usamos la interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecarios")
public class BibliotecarioController {

    @Autowired
    private IBibliotecarioService bibliotecarioService; // Inyectamos el SERVICE, no el repo

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
            Bibliotecario guardado = bibliotecarioService.guardar(bibliotecario);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            // Si el validador del Service lanza error, aquí lo atrapamos
            return ResponseEntity.badRequest().body(e.getMessage());
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