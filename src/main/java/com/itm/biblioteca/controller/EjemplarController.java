package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.service.IEjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    @Autowired
    private IEjemplarService ejemplarService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    public List<Ejemplar> obtenerTodas() {
        return ejemplarService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ejemplar> obtenerPorId(@PathVariable String id) {
        return ejemplarService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearEjemplar(@RequestBody Ejemplar ejemplar) {
        try {
            Ejemplar guardada = ejemplarService.guardar(ejemplar);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEjemplar(@PathVariable String id) {
        try {
            ejemplarService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}