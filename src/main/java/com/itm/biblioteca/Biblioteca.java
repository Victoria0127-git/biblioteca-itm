
package com.itm.biblioteca;
import javax.swing.JOptionPane;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.model.Miembro;

public class Biblioteca {

    
    public static void main(String[] args) {
        int op1=0;
        int op = 0;
        int op2 = 1;
        JOptionPane.showMessageDialog(null,"Menú Bilblioteca");
        while (op2 == 1){
        op1 = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese 1 si quiere administrar los miembros o Ingrese 2 si quiere administrar los libros."));
        if (op1==1){
            
            do{
                op = Integer.parseInt(JOptionPane.showInputDialog(null,"\nIngrese el número del inciso que desee realizar:\n"
                    + "1. Añadir un nuevo miembro al inicio de la lista.\n"
                    + "2. Añadir un nuevo miembro al final de la lista \n"
                    + "3. Imprimir la lista de todos los miembros \n"
                    + "4. Imprimir el primer miembro de la lista \n"
                    + "5. Buscar miembro \n"
                    + "6. Eliminar miembro\n"
                    + "7. Consultar cantidad de miembros\n"
                    + "8. Insertar en la posición deseada\n"
                    + "0. salir\n"));
                switch (op){
                    case 1:
                        String nombre = JOptionPane.showInputDialog(null,"Ingrese nombre del nuevo miembro");
                        int id = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese el id del nuevo miembro"));
                        Miembro nuevo = new Miembro (nombre, id);
                        ManejoMiembros.addInicio(nuevo);
                        
                        break;
                    case 2:
                        nombre = JOptionPane.showInputDialog(null,"Ingrese nombre del nuevo miembro");
                        id = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese el id del nuevo miembro"));
                        Miembro nuevof = new Miembro (nombre, id);
                        ManejoMiembros.addFinal(nuevof);
                        break;
                    case 3:
                        ManejoMiembros.imp();
                        break;    
                    case 4:
                        ManejoMiembros.impPrimerNodo();
                        break;    
                    case 5:
                        String nombrebs = JOptionPane.showInputDialog(null,"Ingrese nombre del miembro a buscar");
                        int idbs = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese el id del miembro a buscar"));
                        ManejoMiembros.Buscar(idbs, nombrebs);
                        break;    
                    case 6:
                        String nomelim = JOptionPane.showInputDialog(null,"Ingrese nombre del miembro a eliminar");
                        int idelim = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese el id del miembro a eliminar"));
                        ManejoMiembros.eliminarPorValor(idelim, nomelim);
                        break;
                    case 7:
                        ManejoMiembros.tamaño();
                        break;    
                    case 8:
                        nombre = JOptionPane.showInputDialog(null,"Ingrese nombre del nuevo miembro");
                        id = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese el id del nuevo miembro"));
                        Miembro nuevop = new Miembro (nombre, id);
                        int posicion = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la posicion en la que desea añadir al nuevo miembro(contando desde 0)"));
                        ManejoMiembros.insertarEnPosicion(posicion, nuevop);
                        break;    
                    case 0:
                        JOptionPane.showMessageDialog(null,"Fin programa");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Error");
                        break;
                        
                        
                        
                }
            }while (op!=0);
        }else if (op1==2) {

            do {
                op = Integer.parseInt(JOptionPane.showInputDialog(null, "\nIngrese el número del inciso que desee realizar:\n"
                        + "1.  Añadir un nuevo libro al inicio de la lista.\n"
                        + "2.  Añadir un nuevo libro al final de la lista \n"
                        + "3.  Imprimir la lista de todos los libros \n"
                        + "4.  Imprimir el primer libro de la lista \n"
                        + "5.  Buscar libro \n"
                        + "6.  Eliminar libro\n"
                        + "7.  Consultar cantidad de libros\n"
                        + "8.  Insertar en la posición deseada\n"
                        + "9.  Consultar si un libro está prestado\n"
                        + "10. Prestar un libro\n"
                        + "0.  salir\n"));

                switch (op) {
                    case 1:
                        String libro = JOptionPane.showInputDialog(null, "Ingrese nombre del nuevo libro");
                        String autor = JOptionPane.showInputDialog(null, "Ingrese el autor del nuevo libro");
                        String isbn = JOptionPane.showInputDialog(null, "Ingrese el isbn del nuevo libro");
                        boolean prestado = false;
                        Libro nuevo = new Libro(libro, autor, isbn, prestado);
                        ManejoLibros.addalInicio(nuevo);
                        break;
                    case 2:
                        libro = JOptionPane.showInputDialog(null, "Ingrese nombre del nuevo libro");
                        autor = JOptionPane.showInputDialog(null, "Ingrese el autor del nuevo libro");
                        isbn = JOptionPane.showInputDialog(null, "Ingrese el isbn del nuevo libro");
                        prestado = false;
                        Libro nuevof = new Libro(libro, autor, isbn, prestado);
                        ManejoLibros.addalFinal(nuevof);
                        break;
                    case 3:
                        ManejoLibros.imp();
                        break;
                    case 4:
                        ManejoLibros.impPrimerNodo();
                        break;
                    case 5:
                        String librobs = JOptionPane.showInputDialog(null, "Ingrese nombre del libro a buscar");
                        String autorbs = JOptionPane.showInputDialog(null, "Ingrese el autor del libro a buscar");
                        String isbnbs = JOptionPane.showInputDialog(null, "Ingrese isbn del libro a buscar");
                        ManejoLibros.Buscar(librobs, autorbs, isbnbs);
                        break;
                    case 6:
                        String libelim = JOptionPane.showInputDialog(null, "Ingrese nombre del libro a eliminar");
                        String autelim = JOptionPane.showInputDialog(null, "Ingrese el autor del libro a eliminar");
                        String isbnelim = JOptionPane.showInputDialog(null, "Ingrese isbn del libro a eliminar");
                        ManejoLibros.eliminarPorValor(libelim, autelim, isbnelim);
                        break;
                    case 7:
                        ManejoLibros.tamaño();
                        break;
                    case 8:
                        libro = JOptionPane.showInputDialog(null, "Ingrese nombre del nuevo libro");
                        autor = JOptionPane.showInputDialog(null, "Ingrese el autor del nuevo libro");
                        isbn = JOptionPane.showInputDialog(null, "Ingrese el isbn del nuevo libro");
                        prestado = false;
                        Libro nuevop = new Libro(libro, autor, isbn, prestado);
                        int posicion = Integer.parseInt(ES.lea("Ingrese la posicion en la que desea añadir al nuevo miembro(contando desde 0)"));
                        ManejoLibros.insertarEnPosicion(posicion, nuevop);
                        break;
                    case 9:
                        String libp = JOptionPane.showInputDialog(null, "Ingrese nombre del libro");
                        String autp = JOptionPane.showInputDialog(null, "Ingrese el autor del libro");
                        ManejoLibros.prestado(libp, autp);

                        break;
                    case 10:
                        String lib = JOptionPane.showInputDialog(null, "Ingrese el nombre del libro");
                        String aut = JOptionPane.showInputDialog(null, "Ingrese el autor del libro");
                        String is = JOptionPane.showInputDialog(null, "Ingrese el isbn del libro");
                        ManejoLibros.prestar(lib, aut, is);
                        break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Fin programa");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error");
                        break;
                }

            } while (op != 0);

        }
            op2 = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese 1 para continuar y 2 para salir"));
        }
    }
}