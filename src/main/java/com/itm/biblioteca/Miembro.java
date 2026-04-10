
package com.itm.biblioteca;


public class Miembro {
    protected String nombre;
    protected int id;
    
    Miembro sig;
    Miembro ant;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Miembro(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.ant= null;
        this.sig = null;
    }
    
    
}
