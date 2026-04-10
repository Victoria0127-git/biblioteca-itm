
package com.itm.biblioteca;


import javax.swing.*;

public class ManejoLibros {
    
    private static Libro cabeza = null;
    private static Libro cola = null;
    
    public static boolean vacio (){
        if (cabeza == null && cola==null ){
            return true;
        }else{
            return false;
        }
    }
     
    public static void addalInicio (Libro nuevo){
        
        
        if (vacio()){
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.sig= cabeza;
            cabeza.ant = nuevo;
            cabeza = nuevo;
        }
        JOptionPane.showMessageDialog(null,"\nSe ingreso el nuevo libro: " + nuevo.getLibro());
    }
    
    public static void addalFinal(Libro nuevof) {
        
        if (cola == null) {
            cabeza = cola = nuevof;
        } else {
            cola.sig = nuevof;
            nuevof.ant = cola;
            cola = nuevof;
        }
        JOptionPane.showMessageDialog(null,"\nSe ingreso el nuevo libro: " + nuevof.getLibro());
    }
    
    public static void imp (){
        Libro aux = cabeza;
        JOptionPane.showMessageDialog(null,"Lista:");
        while (aux != null){
            JOptionPane.showMessageDialog(null,aux.libro+" " + aux.autor + " " + aux.isbn+ " está prestado?: " + aux.prestado + " ");
            aux=aux.sig;
        }
        System.out.println();
    }
    
     public static boolean primerNodo (){
        
        if (cabeza != null){
            
            return true;
        }
        else{
            return false;
        }
    }
    public static void impPrimerNodo (){
        if (primerNodo()==true){
            JOptionPane.showMessageDialog(null,cabeza.libro + " escrito por " + cabeza.autor + " con isbn " + cabeza.isbn);
        }else{
            JOptionPane.showMessageDialog(null,"No existe primer miembro");
        }
    }
    
    public static void eliminarPorValor (String libelim, String autelim, String isbnelim){
        Libro aux = cabeza;
        
        while (aux != null){
            if (aux.libro.equalsIgnoreCase(libelim) && aux.autor.equalsIgnoreCase(autelim)&& aux.isbn.equalsIgnoreCase(isbnelim)){
                if (aux==cabeza){
                    cabeza=aux.sig;
                    if (cabeza != null){
                        cabeza.ant=null;
                    }else{
                        cola = null;
                    }
                            
                }else if (aux == cola) {
                    cola = aux.ant;
                    if (cola!=null){
                        cola.sig=null;
                        
                    }else{
                        cabeza = null;
                    }
                }
                else{
                    aux.ant.sig=aux.sig;
                    aux.sig.ant=aux.ant;
                }
                JOptionPane.showMessageDialog(null,"Libro  eliminado: " + libelim + " escrito por " + autelim );
                return;
            }
            aux=aux.sig;
        }
        JOptionPane.showMessageDialog(null,"Libro no encontrado");
    }
    
    public static boolean Buscar(String librobs, String autorbs, String isbnbs) {
        Libro aux = cabeza;
        while (aux != null) {
            if (aux.libro.equalsIgnoreCase(librobs) && aux.autor.equalsIgnoreCase(autorbs) && aux.isbn.equalsIgnoreCase(isbnbs)) {
                JOptionPane.showMessageDialog(null,"El libro " + librobs + " escrito por " + autorbs + " existe.");
                return true; 
            }
            aux = aux.sig;
        }
        JOptionPane.showMessageDialog(null,"El libro " + librobs + " escrito por " + autorbs + " no existe.");
        return false; 
        }
    
    
    
    public static Libro Buscarlib(String librobs, String autorbs, String isbnbs) {
        Libro aux = cabeza;
        Libro vacio = null;
        while (aux != null) {
            if (aux.libro.equalsIgnoreCase(librobs) && aux.autor.equalsIgnoreCase(autorbs) && aux.isbn.equalsIgnoreCase(isbnbs)) {
                //System.out.println("El libro " + librobs + " escrito por " + autorbs + " existe.");
                return aux; 
            }
            aux = aux.sig;
        }
        //System.out.println("El libro " + librobs + " escrito por " + autorbs + " no existe.");
        return vacio; 
        }
    
    public static boolean prestado (String libp, String autp){
        Libro aux = cabeza;
        while (aux != null) {
            if (aux.libro.equalsIgnoreCase(libp) && aux.autor.equalsIgnoreCase(autp) && aux.prestado==true ) {
                JOptionPane.showMessageDialog(null,"El libro " + libp + " escrito por " + autp + " está prestado");
                
                return true; 
            }else if (aux.libro.equalsIgnoreCase(libp) && aux.autor.equalsIgnoreCase(autp) && aux.prestado==false ) {
                JOptionPane.showMessageDialog(null,"El libro " + libp + " escrito por " + autp + " no está prestado");
                
                return true; 
            }
            aux = aux.sig;
        }
        JOptionPane.showMessageDialog(null,"El libro " + libp+ " escrito por " + autp + " no existe.");
        return false;
    }
    
    public static void prestar (String lib, String aut, String is){
        if (Buscar(lib, aut, is)){
            Buscarlib(lib, aut, is).setPrestado(true);
            JOptionPane.showMessageDialog(null,"Fue prestado exitosamente");
        }else {
            JOptionPane.showMessageDialog(null,"No existe ese libro");
        }
    }
        
    public static int tamaño() {
        int count = 0;
        Libro aux = cabeza;
        while (aux != null) {
            count++;
            aux = aux.sig;
            }
        JOptionPane.showMessageDialog(null,count + " ");
            return count;
            }
        
        
        public static void insertarEnPosicion(int posicion, Libro nuevop) {
            if (posicion < 0) throw new IndexOutOfBoundsException("Posición inválida");
                
                if (posicion == 0) {
                    addalInicio(nuevop);
                    return;
                }
            Libro aux = cabeza;
            for (int i = 0; i < posicion - 1 && aux != null; i++) {
                aux = aux.sig;
            }
            if (aux == null || aux == cola) {
                addalFinal(nuevop);
            } else {
                nuevop.sig = aux.sig;
                aux.sig.ant = nuevop;
                aux.sig = nuevop;
                nuevop.ant = aux;
            }
    }
        
}
    

