package biblioteca.Model;

public class Miembro {
    //Encapsulate atributes
    private String ID_Miembro;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String Telefono;

    //Constructor vacio para el framework
    public Miembro(){};

    //Constructor con parametros
    public Miembro (String ID_Miembro, String Nombre, String Apellido, String Direccion, String Telefono)
    {
        this.ID_Miembro = ID_Miembro;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
    }

    //Getters y Setters
    public String getID_Miembro() {return ID_Miembro;}
    public void setID_Miembro(String ID_Miembro)
    {
        this.ID_Miembro = ID_Miembro;
    }

    public String getNombre() {return Nombre;}
    public void setNombre(String Nombre) {this.Nombre = Nombre;}

    public String getApellido() {return Apellido;}
    public void setApellido(String Apellido) {this.Apellido = Apellido;}

    public  String getDireccion() {return Direccion;}
    public void setDireccion(String Direccion) {this.Direccion = Direccion;}

    public String getTelefono() {return Telefono;}
    public void setTelefono(String Telefono) {this.Telefono = Telefono;}
}

