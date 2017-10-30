/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg1;

import java.util.Scanner;

/**
 *
 * @author abelillo
 */
public class SHA1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);
        metodosSHA1 metodos = new metodosSHA1();
        System.out.println("Introduzca el Mensaje");
        String texto=sc.nextLine();
        metodos.metodoGeneral(texto);


                
    }
    
}
