package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecarios")
public class BibliotecarioController {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @GetMapping
    public List<Bibliotecario> obtenerTodos() {
        return bibliotecarioRepository.findAll();
    }

    @PostMapping
    public Bibliotecario crearBibliotecario(@RequestBody Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    @GetMapping("/{id}")
    public Bibliotecario obtenerPorId(@PathVariable String id) {
        return bibliotecarioRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminarBibliotecario(@PathVariable String id) {
        bibliotecarioRepository.deleteById(id);
    }
}