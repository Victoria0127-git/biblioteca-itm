/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;
import java.io.*;

public class ES {
  public static void imp(String mensaje){
      System.out.println(mensaje);
  }  
  
  public static String lea(String mensaje){
      String dato="";
      InputStreamReader objFlujo=new InputStreamReader(System.in);
      BufferedReader objLeer = new BufferedReader(objFlujo);
      imp(mensaje);
      try{
          dato=objLeer.readLine();
      }catch(IOException er){
          imp("Error de lectura");
      }
      return dato;
  }

    static void imp(boolean existe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

