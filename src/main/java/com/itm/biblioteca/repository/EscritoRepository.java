package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Escrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EscritoRepository extends JpaRepository<Escrito, String> {
    @Query(value = "SELECT MAX(REPLACE(ID_Escrito, 'E', '')) FROM Escrito", nativeQuery = true)
    Integer findMaxIdNumeric();
}