
package com.itm.biblioteca;

import javax.swing.*;

public class ManejoMiembros {
    private static Miembro cabeza = null;
    private static Miembro cola = null;
    
    public static boolean vacio (){
        if (cabeza == null && cola==null ){
            return true;
        }else{
            return false;
        }
    }
     
    public static void addInicio (Miembro nuevo){
        
        
        if (vacio()){
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.sig= cabeza;
            cabeza.ant = nuevo;
            cabeza = nuevo;
        }
        JOptionPane.showMessageDialog(null,"\nSe ingreso el nuevo usuario: " + nuevo.getNombre() + " " + nuevo.getId());
    }
    
    public static void addFinal(Miembro nuevof) {
        
        if (cola == null) {
            cabeza = cola = nuevof;
        } else {
            cola.sig = nuevof;
            nuevof.ant = cola;
            cola = nuevof;
        }
        JOptionPane.showMessageDialog(null,"\nSe ingreso el nuevo usuario: " + nuevof.getNombre() + " " + nuevof.getId());
    }
    
    public static void imp (){
        Miembro aux = cabeza;
        JOptionPane.showMessageDialog(null,"Lista:");
        while (aux != null){
            JOptionPane.showMessageDialog(null,aux.id+" " + aux.nombre + " ");
            aux=aux.sig;
        }

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
            JOptionPane.showMessageDialog(null,cabeza.id + " " + cabeza.nombre);
        }else{
            JOptionPane.showMessageDialog(null,"No existe primer miembro");
        }
    }
    
    public static void eliminarPorValor (int idelim, String nomelim){
        Miembro aux = cabeza;
        
        while (aux != null){
            if (aux.id== idelim && aux.nombre.equalsIgnoreCase(nomelim)){
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
                JOptionPane.showMessageDialog(null,"Miembro  eliminado: " + idelim + " " + nomelim );
                return;
            }
            aux=aux.sig;
        }
        JOptionPane.showMessageDialog(null,"Miembro no encontrado");
    }
    
    public static boolean Buscar(int idbs, String nombs) {
        Miembro aux = cabeza;
        while (aux != null) {
            if (aux.id == idbs && aux.nombre.equalsIgnoreCase(nombs)) {
                JOptionPane.showMessageDialog(null,"El Miembro con id " + idbs + " y nombre " + nombs + " existe.");
                return true; 
            }
            aux = aux.sig;
        }
        JOptionPane.showMessageDialog(null,"El miembro con id " + idbs + " y nombre " + nombs + " no existe.");
        return false; 
        }
        
    public static int tamaño() {
        int count = 0;
        Miembro aux = cabeza;
        while (aux != null) {
            count++;
            aux = aux.sig;
            }
        JOptionPane.showMessageDialog(null,count + " ");
            return count;
            }
        
        
        public static void insertarEnPosicion(int posicion, Miembro nuevop) {
            if (posicion < 0) throw new IndexOutOfBoundsException("Posición inválida");
                
                if (posicion == 0) {
                    addInicio(nuevop);
                    return;
                }
            Miembro aux = cabeza;
            for (int i = 0; i < posicion - 1 && aux != null; i++) {
                aux = aux.sig;
            }
            if (aux == null || aux == cola) {
                addFinal(nuevop);
            } else {
                nuevop.sig = aux.sig;
                aux.sig.ant = nuevop;
                aux.sig = nuevop;
                nuevop.ant = aux;
            }
    }
        
}
