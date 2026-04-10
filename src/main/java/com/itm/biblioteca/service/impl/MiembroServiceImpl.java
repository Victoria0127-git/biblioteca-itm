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
    public Optional<Miembro> buscarPorId(Long id) {
        return miembroRepository.findById(id);
    }

    @Override
    public Miembro guardar(Miembro miembro) {
        // Ejemplo de lógica de negocio:
        // Podrías validar que el teléfono no sea nulo antes de guardar
        if (miembro.getTelefono() == null) {
            throw new RuntimeException("El teléfono es obligatorio para el registro");
        }
        return miembroRepository.save(miembro);
    }

    @Override
    public void eliminar(Long id) {
        miembroRepository.deleteById(id);
    }
}