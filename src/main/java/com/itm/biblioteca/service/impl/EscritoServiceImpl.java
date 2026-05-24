package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Escrito;
import com.itm.biblioteca.repository.EscritoRepository;
import com.itm.biblioteca.service.IEscritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Escrito crear(Escrito escrito) {
        return escritoRepository.save(escrito);
    }

    @Override
    public Escrito actualizar(String id, Escrito escrito) {
        return escritoRepository.save(escrito);
    }

    public void eliminar(String id) {
        escritoRepository.deleteById(id);
    }
}
