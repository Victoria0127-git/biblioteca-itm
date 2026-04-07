
package biblioteca;


public class Libro {
    protected String libro;
    protected String autor;
    protected String isbn;
    protected boolean prestado;
    
    Libro sig;
    Libro ant;

    public Libro(String libro, String autor, String isbn, boolean prestado) {
        this.libro = libro;
        this.autor = autor;
        this.isbn = isbn;
        this.prestado = prestado;
        this.sig=null;
        this.ant=null;
    }
    
    
    
    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
    
    
    
}
