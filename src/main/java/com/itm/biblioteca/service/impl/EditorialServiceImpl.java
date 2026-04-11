package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.repository.EditorialRepository;
import com.itm.biblioteca.service.IEditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServiceImpl implements IEditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    @Override
    public List<Editorial> listarTodos() {
        return editorialRepository.findAll();
    }

    @Override
    public Optional<Editorial> buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID de la editorial no puede estar vacío.");
        }
        return editorialRepository.findById(id);
    }

    @Override
    public Editorial guardar(Editorial editorial) {
        // 1. Validar que el objeto no sea nulo
        if (editorial == null) {
            throw new RuntimeException("No se puede guardar una editorial nula.");
        }

        // 2. Validar campos obligatorios
        if (editorial.getId() == null || editorial.getId().isBlank()) {
            throw new RuntimeException("El ID de la editorial es obligatorio.");
        }

        if (editorial.getNombre() == null || editorial.getNombre().isBlank()) {
            throw new RuntimeException("El nombre de la editorial es obligatorio.");
        }

        // 3. Validar longitud de teléfono (opcional)
        if (editorial.getTelefono() != null && editorial.getTelefono().length() < 7) {
            throw new RuntimeException("El teléfono debe tener al menos 7 dígitos.");
        }

        return editorialRepository.save(editorial);
    }

    @Override
    public void eliminar(String id) {
        // Validar si existe antes de intentar borrar
        if (!editorialRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: La editorial con ID " + id + " no existe.");
        }
        editorialRepository.deleteById(id);
    }
}