package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Prestamo;
import com.itm.biblioteca.repository.PrestamoRepository;
import com.itm.biblioteca.service.IPrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements IPrestamoService {
    private final PrestamoRepository prestamoRepository;

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
    public Prestamo crear(Prestamo prestamo) {
        // 1. Verificación: ¿Vienen los objetos relacionados?
        if (prestamo.getMiembro() == null || prestamo.getEjemplar() == null || prestamo.getBibliotecario() == null) {
            throw new RuntimeException("Error: Un préstamo debe tener un miembro, un ejemplar y un bibliotecario asignado.");
        }

        if (prestamoRepository.existsById(prestamo.getIdPrestamo()))
        {
            throw new RuntimeException("Ya existe prestamo con id "+prestamo.getIdPrestamo());
        }

        // 2. Verificación: Fechas lógicas
        if (prestamo.getFechaDevolucion().isBefore(prestamo.getFechaPrestamo())) {
            throw new RuntimeException("La fecha de devolución no puede ser anterior a la fecha de préstamo.");
        }

        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo actualizar(String id, Prestamo prestamoActual){
        return prestamoRepository.findById(id).
                map(prestamoExistente -> {
                    prestamoExistente.setFechaPrestamo(prestamoActual.getFechaPrestamo());
                    prestamoExistente.setFechaDevolucion(prestamoActual.getFechaDevolucion());
                    prestamoExistente.setMiembro(prestamoActual.getMiembro());
                    prestamoExistente.setBibliotecario(prestamoActual.getBibliotecario());
                    prestamoExistente.setEjemplar(prestamoActual.getEjemplar());

                    return prestamoRepository.save(prestamoExistente);
                })
                .orElseThrow(()-> new RuntimeException("El prestamo con el ID "+id+" no existe"));
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