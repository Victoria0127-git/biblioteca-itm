package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.repository.MiembroRepository;
import com.itm.biblioteca.service.IMiembroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MiembroServiceImpl implements IMiembroService {
    private final MiembroRepository miembroRepository;

    @Override
    public List<Miembro> listarTodos() {
        return miembroRepository.findAll();
    }

    @Override
    @Transactional
    public Miembro crear(Miembro miembro) {
        // 1. Verificación de nulidad
        if (miembro == null) {
            throw new IllegalArgumentException("El miembro no puede ser nulo.");
        }

        // 2. Verificación de campos obligatorios (Ejemplo con el nombre)
        if (miembro.getNombre() == null || miembro.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del miembro es obligatorio.");
        }

        //Creamos el id personalizado
        Integer maxNum = miembroRepository.findMaxIdNumeric();
        int siguienteNum  = (maxNum == null) ? 1 : maxNum + 1;
        String nuevoIdMiembro = String.format("M%03d", siguienteNum);

        //Creamos la base de miembro
        Miembro miembroNuevo = Miembro.builder()
                .idMiembro(nuevoIdMiembro)
                .nombre(miembro.getNombre())
                .apellido(miembro.getApellido())
                .correo(miembro.getCorreo())
                .telefono(miembro.getTelefono())
                .direccion(miembro.getDireccion())
                .build();

        return miembroRepository.save(miembroNuevo);
    }

    @Override
    @Transactional
    public Miembro actualizar(String id, Miembro miembroActual){
        return miembroRepository.findById(id)
                .map(miembroExistente -> {
                    // Validamos que el dato NUEVO no sea nulo ni vacío antes de sobreescribir
                    if (miembroActual.getNombre() != null && !miembroActual.getNombre().isBlank()) {
                        miembroExistente.setNombre(miembroActual.getNombre());
                    }
                    if (miembroActual.getApellido() != null && !miembroActual.getApellido().isBlank()) {
                        miembroExistente.setApellido(miembroActual.getApellido());
                    }
                    if (miembroActual.getCorreo() != null && !miembroActual.getCorreo().isBlank()) {
                        miembroExistente.setCorreo(miembroActual.getCorreo());
                    }

                    // Quitamos la restricción sobre el existente para que deje registrar la dirección por primera vez
                    if (miembroActual.getDireccion() != null) {
                        miembroExistente.setDireccion(miembroActual.getDireccion());
                    }
                    if (miembroActual.getTelefono() != null) {
                        miembroExistente.setTelefono(miembroActual.getTelefono());
                    }

                    return miembroRepository.save(miembroExistente); // Aseguramos la persistencia
                })
                .orElseThrow(() -> new RuntimeException("El miembro con el ID " + id + " no existe"));
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
    @Transactional
    public void eliminar(String id) {
        // Verificamos si existe antes de intentar borrar
        if (!miembroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El miembro con ID " + id + " no existe.");
        }
        miembroRepository.deleteById(id);
    }
}