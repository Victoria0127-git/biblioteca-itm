package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.repositorySQL.MiembroRepositorySQL;
import com.itm.biblioteca.service.IMiembroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MiembroServiceImpl implements IMiembroService {
    private final MiembroRepositorySQL miembroRepository;

    @Override
    public List<Miembro> listarTodos() {
        return miembroRepository.getMiembros();
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

        return miembroRepository.insertarMiembro(miembro);
    }

    @Override
    public Miembro actualizar(String id, Miembro miembroActual){
        return miembroRepository.buscarPorId(id).
                map(miembroExistente -> {
                    if ((miembroExistente.getNombre() != null)) {
                        miembroExistente.setNombre(miembroActual.getNombre());
                    }
                    if ((miembroExistente.getApellido() != null)) {
                        miembroExistente.setApellido(miembroActual.getApellido());
                    }
                    if ((miembroExistente.getCorreo() != null)) {
                        miembroExistente.setCorreo(miembroActual.getCorreo());
                    }
                    if ((miembroExistente.getDireccion() != null)) {
                        miembroExistente.setDireccion( miembroActual.getDireccion());
                    }
                    if ((miembroExistente.getTelefono() != null)) {
                        miembroExistente.setTelefono(miembroActual.getTelefono());
                    }

                    return miembroRepository.actualizarMiembro(id, miembroExistente);
                })
                .orElseThrow(()-> new RuntimeException("El miembro con el ID "+id+" no existe"));
    }

    @Override
    public Optional<Miembro> buscarPorId(String id) {
        // Verificamos que el ID no venga vacío
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
        return miembroRepository.buscarPorId(id);
    }

    @Override
    public void eliminar(String id) {
        // Verificamos si existe antes de intentar borrar
        if (!miembroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El miembro con ID " + id + " no existe.");
        }
        miembroRepository.eliminarMiembro(id);
    }
}