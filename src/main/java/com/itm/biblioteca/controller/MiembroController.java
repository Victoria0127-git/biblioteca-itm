package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miembros")
public class MiembroController {

    @Autowired
    private MiembroRepository miembroRepository;

    @GetMapping
    public List<Miembro> obtenerTodos() {
        return miembroRepository.findAll();
    }

    @PostMapping
    public Miembro crearMiembro(@RequestBody Miembro miembro) {
        
        return miembroRepository.save(miembro);
    }

    @GetMapping("/{id}")
    public Miembro obtenerPorId(@PathVariable Integer id) {
        return miembroRepository.findById(id).orElse(null);
    }


    @DeleteMapping("/{id}")
    public void eliminarMiembro(@PathVariable Integer id) {
        miembroRepository.deleteById(id);
    }
}