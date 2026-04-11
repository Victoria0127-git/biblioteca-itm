package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.repository.EjemplarRepository;
import com.itm.biblioteca.service.IEjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjemplarServiceImpl implements IEjemplarService {

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Override
    public List<Ejemplar> listarTodos() {
        return ejemplarRepository.findAll();
    }

    @Override
    public Optional<Ejemplar> buscarPorId(String id) {
        return ejemplarRepository.findById(id);
    }

    @Override
    public Ejemplar guardar(Ejemplar ejemplar) {
        // Verificación de lógica de negocio:
        // Si es un ejemplar nuevo, podrías setear el estado por defecto a 'true' (disponible)
        if (ejemplar.getId() == null) {
            ejemplar.setEstado(true);
        }

        if (ejemplar.getLibro() == null) {
            throw new RuntimeException("Un ejemplar debe estar asociado a un Libro (ISBN).");
        }

        return ejemplarRepository.save(ejemplar);
    }

    @Override
    public void eliminar(String id) {
        if (!ejemplarRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el ejemplar con ID: " + id);
        }
        ejemplarRepository.deleteById(id);
    }
}