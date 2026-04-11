package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Prestamo;
import com.itm.biblioteca.repository.PrestamoRepository;
import com.itm.biblioteca.service.IPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements IPrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Optional<Prestamo> buscarPorId(String id) {
        return prestamoRepository.findById(id);
    }

    @Override
    @Transactional
    public Prestamo guardar(Prestamo prestamo) {
        // 1. Verificación: ¿Vienen los objetos relacionados?
        if (prestamo.getMiembro() == null || prestamo.getEjemplar() == null || prestamo.getBibliotecario() == null) {
            throw new RuntimeException("Error: Un préstamo debe tener un miembro, un ejemplar y un bibliotecario asignado.");
        }

        // 2. Verificación: Fechas lógicas
        if (prestamo.getFechaDevolucion().before(prestamo.getFechaPrestamo())) {
            throw new RuntimeException("La fecha de devolución no puede ser anterior a la fecha de préstamo.");
        }

        // 3. Lógica extra (Opcional): Aquí podrías verificar si el ejemplar está disponible
        // antes de guardar, llamando a ejemplarRepository.findById(...)

        return prestamoRepository.save(prestamo);
    }

    @Override
    @Transactional
    public void eliminar(String id) {
        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El préstamo con ID " + id + " no existe.");
        }
        prestamoRepository.deleteById(id);
    }
}