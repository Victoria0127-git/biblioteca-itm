package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, String> {
    @Query(value = "SELECT MAX(REPLACE(ID_Prestamo, 'P', '')) FROM Prestamo", nativeQuery = true)
    Integer findMaxIdNumeric();

    boolean existsByMiembro_IdMiembroAndFechaDevolucionIsNull(String idMiembro);

    boolean existsByEjemplar_Libro_IsbnAndFechaDevolucionIsNull(String isbn);
}