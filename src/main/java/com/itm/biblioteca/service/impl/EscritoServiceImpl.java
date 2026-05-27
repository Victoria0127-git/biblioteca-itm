package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Autor;
import com.itm.biblioteca.model.Escrito;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.EscritoRepository;
import com.itm.biblioteca.service.IEscritoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EscritoServiceImpl implements IEscritoService {
    private final EscritoRepository escritoRepository;

    @Override
    public List<Escrito> listarTodos() {
        return escritoRepository.findAll();
    }

    @Override
    public Optional<Escrito> buscarPorId(String id) {
        return escritoRepository.findById(id);
    }

    @Override
    @Transactional
    public Escrito crearYGuardarEscrito(Libro libro, String idAutor) {
        //Generamos un ID personalizado Automático
        Integer maxNumero = escritoRepository.findMaxIdNumeric();
        int siguienteNumero = (maxNumero == null) ? 1 : maxNumero + 1;
        String nuevoIdEscrito = String.format("ES%03d", siguienteNumero);

        //Mapeamos un autor cascaron
        Autor autorcascaron = Autor.builder().idAutor(idAutor).build();

        //Construimos el objeto Escrito con valores predeterminados
        Escrito escritoNuevo = Escrito.builder()
                .id(nuevoIdEscrito)
                .fechaEscrito(LocalDate.now())
                .ciudad("Sin especificar")
                .libro(libro)
                .autor(autorcascaron)
                .build();

        return escritoRepository.save(escritoNuevo);
    }
    @Override
    public Escrito crear(Escrito entidad) {
        throw new UnsupportedOperationException("Para crear un escrito, usa el método crearYGuardarEscrito(Libro, String)");
    }

    @Override
    public Escrito actualizar(String id, Escrito escrito) {
        return escritoRepository.save(escrito);
    }

    @Override
    public void eliminar(String id) {
        escritoRepository.deleteById(id);
    }
}
