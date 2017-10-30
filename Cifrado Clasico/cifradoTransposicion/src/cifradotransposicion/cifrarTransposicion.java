package cifradoTransposicion;

import java.util.Scanner;

public class cifrarTransposicion{
	
	static Scanner entrada = new Scanner (System.in);	
	public static String cadena;
	public static String cadenaCifrada;
	static int clave;	
	public static String[] arrayCadena;
	public static int i;
	public static int x;
	public static int y;
	public static int tamanoCadena;
	public static int contador;
	public static char matriz[][];
	public static int q;
	public static int resto;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("\nPor favor, introduzca la cadena de texto que quiere cifrar sin espacios entre palabras:\n");
		
		String cadena=entrada.nextLine();//Leemos por teclado la cadena de texto a transponer.
		
		System.out.println("\nPor favor, introduzca la clave con la que se quiere cifrar el mensaje por transposición:\n");
		
		clave=entrada.nextInt();//Introducimos por teclado la clave con la que se transpone la cadena de texto. 
		
		System.out.println("La cadena a cifrar es la siguiente: "+cadena+"\n");
		
		System.out.println("La clave con la que se va a cifrar la cadena de texto es la siguiente: "+clave+"\n");
		
		tamanoCadena=cadena.length();//Obtención de la longitud de la cadena.
		
		System.out.println("El tamaño de la cadena es: "+tamanoCadena+"\n");
		
		cadenaCifrada="";
		
		contador=0;//Contador para saber si llegamos al final de la cadena y coger clave+1 como clave porque con la primera vuelta ya se descartaron caracteres.
		
		do {
			//Recorremos la cadena de texto para obtener los caracteres de la cadena en función de la clave.
			for (i=contador; i<tamanoCadena; i+=clave){
				
				cadenaCifrada+=cadena.charAt(i);			
				
			}
			
			contador ++;//Con cada vuelta, es decir, cada vez que llegamos al final de la cadena de caracteres, proseguimos con la transposición pero teniendo en cuenta que hay caracteres que fueron borrados anteriormente.
			
		}while(contador<clave);//Cuando el contador deja de ser estrictamente menor a la clave, se dejan de transponer los caracteres porque ya no existen más caracteres para transponer.
			
		System.out.println("La cadena cifrada es la siguiente: "+cadenaCifrada+"\n");
		
	}
}
