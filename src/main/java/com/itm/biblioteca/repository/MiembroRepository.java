package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
    // Ejemplo: Miembro findByDni(String dni);
}