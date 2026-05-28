package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, String> {
    @Query(value = "SELECT MAX(REPLACE(ID_Ejemplar, 'J', '')) FROM Ejemplar", nativeQuery = true)
    Integer findMaxIdNumeric();
}