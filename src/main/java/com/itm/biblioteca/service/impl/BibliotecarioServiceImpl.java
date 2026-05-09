package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.repository.BibliotecarioRepository;
import com.itm.biblioteca.service.IBibliotecarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BibliotecarioServiceImpl implements IBibliotecarioService {
    private final BibliotecarioRepository bibliotecarioRepository;

    @Override
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAll();
    }

    @Override
    public Optional<Bibliotecario> buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID del bibliotecario no puede estar vacío.");
        }
        return bibliotecarioRepository.findById(id);
    }

    @Override
    public Bibliotecario crear(Bibliotecario bibliotecario) {
        // 1. Validar que el objeto no sea nulo
        if (bibliotecario == null) {
            throw new RuntimeException("No se puede guardar un ID nulo.");
        }

        if (bibliotecarioRepository.existsById(bibliotecario.getIdBibliotecario())){
            throw new RuntimeException("Ya existe el bibliotecario con el ID: "+bibliotecario.getIdBibliotecario());
        }

        // 2. Validar campos obligatorios
        if (bibliotecario.getIdBibliotecario() == null || bibliotecario.getIdBibliotecario().isBlank()) {
            throw new RuntimeException("El ID es obligatorio.");
        }

        if (bibliotecario.getNombre() == null || bibliotecario.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del bibliotecario es obligatorio.");
        }

        if (bibliotecario.getApellido() == null || bibliotecario.getApellido().isBlank()) {
            throw new RuntimeException("El apellido del bibliotecario es obligatorio.");
        }

        return bibliotecarioRepository.save(bibliotecario);
    }

    @Override
    public Bibliotecario actualizar(String id, Bibliotecario biblioActualizado){
        return bibliotecarioRepository.findById(id).
                map(biblioExistente -> {
                    if ((biblioActualizado.getNombre() != null)) {
                        biblioExistente.setNombre(biblioActualizado.getNombre());
                    }

                    if ((biblioActualizado.getApellido() != null)) {
                        biblioExistente.setApellido(biblioActualizado.getApellido());
                    }

                    return bibliotecarioRepository.save(biblioExistente);
                })
                .orElseThrow(()-> new RuntimeException("El bibliotecario con el ID "+id+" no existe"));
    }

    @Override
    public void eliminar(String id) {
        // Validar si existe antes de intentar borrar
        if (!bibliotecarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: La editorial con ID " + id + " no existe.");
        }
        bibliotecarioRepository.deleteById(id);
    }
}