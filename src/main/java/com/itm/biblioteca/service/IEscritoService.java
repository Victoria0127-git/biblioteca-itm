package com.itm.biblioteca.service;

import com.itm.biblioteca.model.Escrito;
import com.itm.biblioteca.model.Libro;

public interface IEscritoService extends IBaseService <Escrito, String>{
    //Personalizado
    Escrito crearYGuardarEscrito(Libro libro, String idAutor);
}
