package vigenere;

import java.io.*;

public class main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

        int opcion;
        
        do{
            InputStreamReader entradaTeclado=new InputStreamReader(System.in);//Lectura de la entrada por teclado.
            BufferedReader buffer=new BufferedReader(entradaTeclado);//Almacenamiento en memoria de la opción elegida.
            
            String mensajeClaro;//Variable para el texto que quiere cifrar el usuario.
            String clave;//Variable para la clave utilizada por el usuario para cifrar el texto.
            
            System.out.println("\nPor favor, elija la opción que desee realizar:\n\n");
            System.out.println("1:-Cifrado Vigenere\n");//Opción 1 del menú.
            System.out.println("2:-Descifrado Vigenere\n");//Opción 2 del menú.
            System.out.println("3:-Salir\n");//Opción 3 del menú.
            
            opcion=Integer.parseInt(buffer.readLine());//Lectura por teclado de la opción elejida por el usuario.
            
            switch(opcion){
            
                case 1:
                	
                    System.out.println("\nPor favor, introduzca el mensaje que desee cifrar:\n");
                    mensajeClaro=buffer.readLine();
                    mensajeClaro=mensajeClaro.trim();
                    
                    System.out.println("\nPor favor, introduzca la clave con la que desee cifrar el mensaje: \n");
                    clave=buffer.readLine();
                    
                    //Instanciamiento de un objeto de la clase para llamar al proceso de cifrado con los argumentos introducidos por teclado.
                    vigenere objetoCifrar=new vigenere();
                    
                    //Llamada al método de cifrado y muestra por pantalla.
                    System.out.println("\nSu criptograma resultante es el siguiente:\n\n"+objetoCifrar.cifrar(mensajeClaro,clave));
                    
                    break;
                    
                case 2:
                	
                    System.out.println("\nPor favor, introduzca el criptograma que desee desencriptar: \n");
                    mensajeClaro=buffer.readLine();
                    mensajeClaro=mensajeClaro.trim();
                    
                    System.out.println("\nPor favor, introduzca la clave con la que desea desencriptar el criptograma: \n");
                    clave=buffer.readLine();
                    
                    //Instanciamiento de un objeto de la clase para llamar al proceso de cifrado con los argumentos introducidos por teclado.
                    vigenere objetoDescifrar=new vigenere ();
                    
                    //Llamada al método de cifrado y muestra por pantalla.
                    System.out.println("\nSu criptograma descifrado es el siguiente:\n\n"+objetoDescifrar.descifrar(mensajeClaro,clave));
                    
                    break;
                    
                case 3:
                	
                	System.out.print("\nGRACIAS POR UTILIZAR NUESTRA APLICACIÓN\t\t\t\t by Iván Barbado, Abel de Andrés y Diego Monjas" );
                	
                	break;
                    
                default:
                	
                    System.out.print("\nPor favor, elija una opción correcta. Inténtelo de nuevo.\n" );
                    
            }
            
        }while(opcion!=3);
    }
    
}


