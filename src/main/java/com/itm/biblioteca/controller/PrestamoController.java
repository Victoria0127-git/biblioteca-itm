package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Prestamo;
import com.itm.biblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    @PostMapping
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @GetMapping("/{id}")
    public Prestamo obtenerPorId(@PathVariable String id) { // Cambiado a String
        return prestamoRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void finalizarPrestamo(@PathVariable String id) { // Cambiado a String
        prestamoRepository.deleteById(id);
    }
}