package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.repository.MiembroRepository;
import com.itm.biblioteca.service.IMiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MiembroServiceImpl implements IMiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Override
    public List<Miembro> listarTodos() {
        return miembroRepository.findAll();
    }

    @Override
    public Miembro guardar(Miembro miembro) {
        // 1. Verificación de nulidad
        if (miembro == null) {
            throw new IllegalArgumentException("El miembro no puede ser nulo.");
        }

        // 2. Verificación de campos obligatorios (Ejemplo con el nombre)
        if (miembro.getNombre() == null || miembro.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del miembro es obligatorio.");
        }

        // 3. Verificación de duplicados (Suponiendo que el ID es el documento único)
        if (miembroRepository.existsById(miembro.getIdMiembro())) {
            // Aquí podrías decidir si quieres actualizar o lanzar error
            // Por ahora, lo guardamos (save en JPA actualiza si el ID ya existe)
        }

        return miembroRepository.save(miembro);
    }

    @Override
    public Optional<Miembro> buscarPorId(String id) {
        // Verificamos que el ID no venga vacío
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
        return miembroRepository.findById(id);
    }

    @Override
    public void eliminar(String id) {
        // Verificamos si existe antes de intentar borrar
        if (!miembroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El miembro con ID " + id + " no existe.");
        }
        miembroRepository.deleteById(id);
    }
}