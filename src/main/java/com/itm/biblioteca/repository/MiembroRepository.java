package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, String> {
    @Query(value = "SELECT MAX(REPLACE(ID_Miembro, 'M', '')) FROM Miembro", nativeQuery = true)
    Integer findMaxIdNumeric();

    List<Miembro> findByEstadoTrue();
}