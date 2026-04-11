package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Autor;
import com.itm.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 
import java.util.List; 

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping
    public List<Autor> obtenerTodos() {
        return autorRepository.findAll();
    }

    @PostMapping
    public Autor crearAutor(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }

    @GetMapping("/{id}")
    public Autor obtenerPorId(@PathVariable String id) {
        return autorRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminarAutor(@PathVariable String id) {
        autorRepository.deleteById(id);
    }
}