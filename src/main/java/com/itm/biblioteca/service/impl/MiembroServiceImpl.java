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
    public Miembro crear(Miembro miembro) {
        // 1. Verificación de nulidad
        if (miembro == null) {
            throw new IllegalArgumentException("El miembro no puede ser nulo.");
        }

        if (miembroRepository.existsById(miembro.getIdMiembro())){
            throw new IllegalArgumentException("El miembro existe en el sistema.");
        }

        // 2. Verificación de campos obligatorios (Ejemplo con el nombre)
        if (miembro.getNombre() == null || miembro.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del miembro es obligatorio.");
        }

        return miembroRepository.save(miembro);
    }

    @Override
    public Miembro actualizar(String id, Miembro miembroActual){
        return miembroRepository.findById(id).
                map(miembroExistente -> {
                    miembroExistente.setNombre(miembroActual.getNombre());
                    miembroExistente.setApellido(miembroActual.getApellido());
                    miembroExistente.setCorreo(miembroActual.getCorreo());
                    miembroExistente.setDireccion( miembroActual.getDireccion());
                    miembroExistente.setTelefono(miembroActual.getTelefono());

                    return miembroRepository.save(miembroExistente);
                })
                .orElseThrow(()-> new RuntimeException("El miembro con el ID "+id+" no existe"));
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