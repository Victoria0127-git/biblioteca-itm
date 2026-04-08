package com.itm.biblioteca.controller;

import com.itm.biblioteca.Libro;
import com.itm.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return libroRepository.findById(id).orElse(null);
    }
}