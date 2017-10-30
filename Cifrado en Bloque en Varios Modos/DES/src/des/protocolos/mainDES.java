package des.protocolos;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author abelillo
 */
public class mainDES {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        metodosDES metodos = new metodosDES();
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el Mensaje");
        String mensaje=sc.nextLine();
        mensaje=StringAHex(mensaje);
        
        System.out.println("El mensaje en Hexadecimal es: "+mensaje);
        System.out.println("Introduzca la clave");
        String clave=sc.nextLine();
        clave=StringAHex(clave);
        
        //String clave="133457799BBCDFF1";//133457799BBCDFF1
        //String clave=sc.nextLine();
        System.out.println("La clave en Hexadecimal es: "+clave);
        metodos.metodoPrincipal(clave,mensaje);     

    }
    
    public static String StringAHex(String str){
 
	  char[] chars = str.toCharArray();
 
	  StringBuffer hex = new StringBuffer();
	  for(int i = 0; i < chars.length; i++){
	    hex.append(Integer.toHexString((int)chars[i]));
	  }
 
	  return hex.toString();
  }
    
}


