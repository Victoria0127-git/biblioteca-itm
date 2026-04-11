package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Escrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscritoRepository extends JpaRepository<Escrito, String> {

}