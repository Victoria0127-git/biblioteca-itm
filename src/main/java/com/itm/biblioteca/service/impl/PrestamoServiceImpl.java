package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.model.Prestamo;
import com.itm.biblioteca.repository.EjemplarRepository;
import com.itm.biblioteca.repository.PrestamoRepository;
import com.itm.biblioteca.service.IPrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements IPrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;

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

        // 2. Verificación: Fechas lógicas (Permite mismo día)
        if (prestamo.getFechaDevolucion() != null && prestamo.getFechaPrestamo() != null) {
            if (prestamo.getFechaDevolucion().isBefore(prestamo.getFechaPrestamo())) {
                throw new RuntimeException("La fecha de devolución no puede ser anterior a la fecha de préstamo.");
            }
        }

        //Validar que el ejemplar no esté prestado ya.Primero buscamos el ejemplar
        Ejemplar ejemplarReal = ejemplarRepository.findById(prestamo.getEjemplar().getId())
                .orElseThrow(() -> new RuntimeException("Error: El ejemplar especificado no existe."));
        // Si el estado es false, significa que ya está prestado
        if (!ejemplarReal.getEstado()) {
            throw new RuntimeException("Error: El ejemplar '" + ejemplarReal.getId() + "' ya está prestado actualmente.");
        }

        //Creamos el id personalizado automáticamente
        Integer maxNum = prestamoRepository.findMaxIdNumeric();
        int siguienteNum = (maxNum == null) ? 1 : maxNum + 1;
        String nuevoIdPrestamo = String.format("P%03d", siguienteNum);

        ejemplarReal.setEstado(false); //Prestamos el ejemplar
        ejemplarRepository.save(ejemplarReal);

        //Construimos la base del prestamo con valores predeterminados
        Prestamo prestamoNuevo = Prestamo.builder()
                .idPrestamo(nuevoIdPrestamo)
                .fechaPrestamo(LocalDate.now())
                .ejemplar(ejemplarReal)
                .miembro(prestamo.getMiembro())
                .bibliotecario(prestamo.getBibliotecario())
                .build();

        return prestamoRepository.save(prestamoNuevo);
    }

    @Override
    @Transactional
    public Prestamo actualizar(String id, Prestamo prestamoActual) {
        return prestamoRepository.findById(id)
                .map(prestamoExistente -> {
                    if (prestamoActual.getFechaPrestamo() != null) {
                        prestamoExistente.setFechaPrestamo(prestamoActual.getFechaPrestamo());
                    }

                    if (prestamoActual.getFechaDevolucion() != null) {
                        prestamoExistente.setFechaDevolucion(prestamoActual.getFechaDevolucion());

                        // Cambiamos su bit a true (1) para que quede Disponible nuevamente
                        if (prestamoExistente.getEjemplar() != null) {
                            prestamoExistente.getEjemplar().setEstado(true);
                        }
                    }

                    if (prestamoActual.getMiembro() != null) {
                        prestamoExistente.setMiembro(prestamoActual.getMiembro());
                    }
                    if (prestamoActual.getBibliotecario() != null) {
                        prestamoExistente.setBibliotecario(prestamoActual.getBibliotecario());
                    }
                    if (prestamoActual.getEjemplar() != null) {
                        prestamoExistente.setEjemplar(prestamoActual.getEjemplar());
                    }

                    return prestamoRepository.save(prestamoExistente);
                })
                .orElseThrow(() -> new RuntimeException("El prestamo con el ID " + id + " no existe"));
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