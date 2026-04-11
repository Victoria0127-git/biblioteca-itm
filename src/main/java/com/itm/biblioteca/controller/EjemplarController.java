package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.repository.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @GetMapping
    public List<Ejemplar> obtenerTodos() {
        return ejemplarRepository.findAll();
    }

    @PostMapping
    public Ejemplar crearEjemplar(@RequestBody Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }

    @GetMapping("/{id}")
    public Ejemplar obtenerPorId(@PathVariable String id) {
        return ejemplarRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminarEjemplar(@PathVariable String id) {
        ejemplarRepository.deleteById(id);
    }
}