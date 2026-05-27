
package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
    List<Libro> findByEstadoTrue();
}