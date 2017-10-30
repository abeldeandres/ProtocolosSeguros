/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacion.vernam;

/**
 *
 * @author abelillo
 */
public class metodos {
    //Metodo para pasar un texto a Binario
     public String textoABinario(String texto)
	    {
	        
		 String textoBinario = "";
	        for(char letra : texto.toCharArray())
	        {
	            textoBinario += String.format("%8s", Integer.toBinaryString(letra));
	        }
	        return textoBinario.replace("\u0020","\u0030"); //Lo que se hace aqui es reemplazar el carater de espacio, por un 0
	    }
	 //Metodo para pasar de Binario a Texto
	 public String BinarioAtexto(String entradaBinario)
	 {  
		 String salidaASCII = ""; //Inicializamos la variable salida en Vacio
		 for(int i = 0; i <= entradaBinario.length() - 8; i+=8) 
		 {
		     int digitoBinario = Integer.parseInt(entradaBinario.substring(i, i+8), 2);
		     salidaASCII += (char) digitoBinario;
		 }
		 return salidaASCII;
	 }
	 //Metodo para calcular el XOR de dos cadenas introducidas
         //Para realizar el XOR es necesario que el tamaño de las cadenas sea iguales en binario
         //Por lo tanto la cadena mas pequeña la tendremos que rellenar con ceros hasta que alcance el tamaño de la
         //cadena mas grande.
	 public String operacionXOR(String mensaje, String secreto)
	 {		
			String menCodifArray = "";
			if (mensaje.length()>secreto.length())
			{
				secreto=this.rellenarConCeros(secreto, mensaje.length(), secreto.length());
			}
			if (mensaje.length()<secreto.length())
			{
				mensaje=this.rellenarConCeros(mensaje, secreto.length(), mensaje.length());
			}
			
			char[] charMensaje = new char[mensaje.length()];
			 charMensaje = mensaje.toCharArray();
			 char[] charSecreto = new char[mensaje.length()];
			 charSecreto=secreto.toCharArray();
			 
			 for (int i=0; i<charMensaje.length; i++)
			 {
				 if(charMensaje[i]==charSecreto[i])
				 {
					 menCodifArray=menCodifArray+"0";
				 }
				 else menCodifArray=menCodifArray+"1";
			 }

			 return menCodifArray;
			
	 }
	 //En la funcion rellenar con 0 indicamos la longitud del tamaño del string mas pequeño
         //y el tamaño del string mas grande. Por lo tanto si el string mayor tiene un tamaño de 12 caracteres
         //y el tamaño del string menor es de 8 caracteres, añadiremos 4 ceros al string mas pequeño para que alcance
         //los 12 caracteres.
	 public String rellenarConCeros(String secretoBin, int tamMen, int tamSec)
	 {
		 for(int i=tamSec; i<tamMen;i++)
			{
				secretoBin=secretoBin+"0";
			}
		 return secretoBin;
	 }
}
