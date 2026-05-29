package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.EjemplarRepository;
import com.itm.biblioteca.service.IEjemplarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EjemplarServiceImpl implements IEjemplarService {
    private final EjemplarRepository ejemplarRepository;

    @Override
    public List<Ejemplar> listarTodos() {
        return ejemplarRepository.findByLibroEstadoTrue();
    }

    @Override
    public Optional<Ejemplar> buscarPorId(String id) {
        return ejemplarRepository.findById(id);
    }

    @Override
    public Ejemplar crear(Ejemplar ejemplar) {
        // Verificación de lógica de negocio:
        if (ejemplar.getLibro() == null) {
            throw new RuntimeException("Un ejemplar debe estar asociado a un Libro (ISBN).");
        }

        //Creamos el ID personalizado
        Integer numMax = ejemplarRepository.findMaxIdNumeric();
        int nextNum = (numMax==null)?1:numMax+1;
        String idPersonalizado = String.format("J"+nextNum);

        //Creamos libroCascaron
        Libro libroCascaron = Libro.builder().isbn(ejemplar.getLibro().getIsbn()).build();

        //Creamos el ejemplar
        Ejemplar nuevoEjemplar = Ejemplar.builder()
                .id(idPersonalizado)
                .libro(libroCascaron)
                .estado(true) //Al crearlo, siempre va a estar por defecto disponible
                .ubicacion(ejemplar.getUbicacion())
                .build();

        return ejemplarRepository.save(nuevoEjemplar);
    }

    @Override
    public Ejemplar actualizar(String id, Ejemplar ejemplarActual) {
        return ejemplarRepository.findById(ejemplarActual.getId()).
                map(ejemplarExiste -> {
                    if ((ejemplarActual.getUbicacion() != null)) {
                        ejemplarExiste.setUbicacion(ejemplarActual.getUbicacion());
                    }
                    if ((ejemplarActual.getEstado() != null)) {
                        ejemplarExiste.setEstado(ejemplarActual.getEstado());
                    }
                    if ((ejemplarActual.getLibro() != null)) {
                        ejemplarExiste.setLibro(ejemplarActual.getLibro());
                    }

                        return ejemplarRepository.save(ejemplarExiste);
                })
                .orElseThrow(()-> new RuntimeException("No existe el ejemplar con ese id"));
    }

    @Override
    public void eliminar(String id) {
        if (!ejemplarRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el ejemplar con ID: " + id);
        }
        ejemplarRepository.deleteById(id);
    }
}