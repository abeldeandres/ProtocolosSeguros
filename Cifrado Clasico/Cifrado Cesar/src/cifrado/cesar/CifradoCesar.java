/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado.cesar;

import java.time.Clock;
import java.util.Scanner;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author abelillo
 */
public class CifradoCesar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Esta es la clase principal del main donde ejecutaremos el programa.
        //La aplicacion debera utilizar palabras con letras del alfabeto ingles, no se han incluido la "ñ" aunque
        //se podria haber implementado, es simplemente añadirla al alfabeto.
        Scanner sc = new Scanner(System.in);
        metodos metodo = new metodos();
		System.out.println("Introduzca una letra como clave");
		String clave = sc.next();
                sc.nextLine();                       
		System.out.println("Introduzca el mensaje a cifrar (se aconseja en minusculas)");
		String mensaje=sc.nextLine();
                String mensajeCifrado=metodo.codificacion(mensaje, clave);
                System.out.println(mensajeCifrado);
                String mensajedesCifrado=metodo.decodificacion(mensajeCifrado, clave);
                System.out.println(mensajedesCifrado);
                

		
    }
    
}
