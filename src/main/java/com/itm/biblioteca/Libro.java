package com.itm.biblioteca;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libro;
    private String autor;
    private String isbn;
    private boolean prestado;

    public Libro() {
    }

    public Libro(String libro, String autor, String isbn, boolean prestado) {
        this.libro = libro;
        this.autor = autor;
        this.isbn = isbn;
        this.prestado = prestado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibro() { return libro; }
    public void setLibro(String libro) { this.libro = libro; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public boolean isPrestado() { return prestado; }
    public void setPrestado(boolean prestado) { this.prestado = prestado; }
}