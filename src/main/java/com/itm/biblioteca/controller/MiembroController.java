package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.service.IMiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miembro")
public class MiembroController {

    @Autowired
    private IMiembroService miembroService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    public List<Miembro> obtenerTodas() {
        return miembroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Miembro> obtenerPorId(@PathVariable String id) {
        return miembroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearMiembro(@RequestBody Miembro miembro) {
        try {
            Miembro guardada = miembroService.guardar(miembro);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMiembro(@PathVariable String id) {
        try {
            miembroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}