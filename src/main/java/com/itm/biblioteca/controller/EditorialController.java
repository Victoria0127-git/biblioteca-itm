package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoriales")
public class EditorialController {

    @Autowired
    private EditorialRepository editorialRepository;

    @GetMapping
    public List<Editorial> obtenerTodas() {
        return editorialRepository.findAll();
    }

    @PostMapping
    public Editorial crearEditorial(@RequestBody Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @GetMapping("/{id}")
    public Editorial obtenerPorId(@PathVariable String id) {
        return editorialRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminarEditorial(@PathVariable String id) {
        editorialRepository.deleteById(id);
    }
}