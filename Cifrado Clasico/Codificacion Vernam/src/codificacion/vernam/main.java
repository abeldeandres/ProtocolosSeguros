/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacion.vernam;

import java.util.Scanner;

/**
 *
 * @author abelillo
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
		metodos metodos = new metodos();

		//SECRETO Y MENSAJE TIENEN QUE TENER LA MISMA LONGITUD
                //Sin embargo, el codigo se ha hecho para que se secreto y mensaje pueden tener la misma
                //o pueden tener distinta longitud. 
                //Para que tuvieran la misma longitud, habria que hacer un while en el que longitud de clave y mensaje fueran iguales
                //y mientras que no sean iguales, se pediran.
		
		System.out.println("Introduzca el Mensaje");
		String texto=sc.nextLine();
		String menBin=metodos.textoABinario(texto);
		System.out.println(menBin);
		
		System.out.println("Introduzca el Secreto");
		String secret=sc.nextLine();
		String secretoBin=metodos.textoABinario(secret);
		System.out.println(secretoBin);
		
		
		System.out.println("Ahora lo pasamos a ASCII otra vez");
		String menASCII=metodos.BinarioAtexto(menBin);
		System.out.println(menASCII);
		
		System.out.println("Este es el mensaje codificado con el Mensaje y el Secreto");
		String mensajeCodificado= metodos.operacionXOR(menBin, secretoBin);
		System.out.println(mensajeCodificado);
		
		
		System.out.println("Decodificamos el mensaje utilizando el mensaje codificado y el secreto");
		String mensajeDecodificado=metodos.operacionXOR(mensajeCodificado, secretoBin);
		System.out.println(mensajeDecodificado);
		System.out.println("Y en ASCII seria:");
		System.out.println(metodos.BinarioAtexto(mensajeDecodificado));
    }
    
}
