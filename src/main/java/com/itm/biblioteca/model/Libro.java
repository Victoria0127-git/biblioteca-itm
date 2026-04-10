package com.itm.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "Libro")
public class Libro {
    @Id
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "NumPag")
    private int numPag;

    @ManyToOne //Llave foranea
    @JoinColumn(name = "ID_Editorial") Editorial editorial;


    public Libro() {
    }

    public Libro(String isbn, String titulo, int numPag, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.numPag = numPag;
        this.editorial = editorial;
    }
}