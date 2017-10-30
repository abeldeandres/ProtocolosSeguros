package vigenere;

import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class vigenere {
    
    char [][]matriz=new char [26][26];//Matriz cuadrada de los 26 símbolos del alfabeto.
    String criptograma=null;

    //CONSTRUCTOR
    public vigenere(){//Constructor de la clase en el que se inicializa una matriz cuadrada de n x n donde n es la cantidad de letras que estemos ocupando para nuestro alfabeto.
    	
        int columna=65;//valor de la A mayuscula.
        int fila=0;

        for(int i=0;i<26;i++){//Construcción de las filas de la matriz.
        
            //Con fila llenaremos las filas de la matriz y con columna las columnas de la matriz.
            fila=columna;
            
            //Nuestro alfabeto tendrá  26 letras.
            for(int j=0;j<26;j++){
            
                matriz[i][j]=(char)fila;
                fila++;

                if(fila>90){//Caso de llegar a la Z.
                
                    //Si es asi regresa y comienza de nuevo con A.
                    fila=65;
                }
            }
            
            columna++;
            
            if(columna>90){//Caso de llegar a la Z.
            
                //Si es asi regresa y comienza con la A.
                columna=65;
            }
        }
    }
   
    //FUNCIÓN PARA QUITAR LOS ESPACIOS EN BLANCO
    String quitarEspacios(String textoEntrada){
    	//Función para quitar los espacios de la cadena de texto de entrada. Ya sea el mensaje o la clave.
        char []auxiliar=textoEntrada.toCharArray();
        textoEntrada="";
        
        for(int i=0;i<auxiliar.length;i++){
        
            if(auxiliar[i]!=' '){//Reconocimiento del espacio.
            
                textoEntrada+=auxiliar[i];
                
            }
        }
        return textoEntrada;
    }
    
    //FUNCIÓN PARA ADECUAR LA LONGITUD DE LA CLAVE A LA LONGITUD DEL MENSAJE
    public String completar(String menor, int tamano){
    	//Construcción del string para que la clave se repita tantas veces hasta completar el tamaño del mensaje.
    	
        int diferencia=0;//Variable auxiliar para saber la diferencia entre clave y mensaje.
        int indice=0;
        
        if(menor.length()!=tamano){//Caso en el que clave y mensaje no sean de igual tamaño.
        	
            //preguntamos si es menor la clave al mensaje
            if(menor.length()<tamano){//Valoración del caso en el que la clave sea menor que el mensaje.
            	
                diferencia=tamano-menor.length();//Diferencia entre ambos.
                while(diferencia!=0){
                	
                    menor+=menor.charAt(indice);//Repetición de la clave hasta obtener la misma longitud que el mensaje.
                    
                    if(indice==(menor.length()-1)){
                    	
                        indice=-1;
                    }
                    
                    indice++;//El indice va aumentando hasta obtener el mismo tamaño que el mensaje.
                    diferencia--;//La diferencia va disminuyendo a medida que el índice aumenta.
                }
            }
            else{//Caso en el que la clave sea mayor que el mensaje.
            
                diferencia=menor.length()-tamano;
                //Se recorta la clave.
                menor=menor.substring(0, menor.length()-diferencia);
            }
        }
        
        return menor;
    }
    
    //MÉTODO PARA OBTENER EL VALOR DE LA MATRIZ
    char getCaracter(char mensaje,char clave){
    	//Identificación de los caracteres a sustituir.
    	
        int posicionMensaje=0;//Indice de la columna de la matriz.
        int posicionClave=0;//Indice de la fila de la matriz.
        int indice=0;//Valor de intersección.
        
        boolean encontrado=false;
        
        
        while(indice<26 && encontrado==false){
        	//Buscamos el caracter del mensaje en las columnas de la matriz.       	
            if(matriz[0][indice]==mensaje){
            	
                encontrado=true;
                posicionMensaje=indice;
            }
            
            indice++;
        }
        encontrado=false;
        indice=0;
        
        while(indice<26 && encontrado==false){
        	//Buscamos el caracter de la clave en las filas de la matriz.      	
            if (matriz[indice][0]==clave){
            	
                encontrado=true;
                posicionClave=indice;
            }
            indice++;
        }

        return matriz[posicionClave][posicionMensaje];//Valor de intersección.
    }

    //FUNICÓN DE CIFRADO
    public String cifrar(String mensaje,String clave){
    	
        criptograma=" ";
        
        mensaje=quitarEspacios(mensaje);//Quitamos los espacios en blanco del mensaje.
        clave=quitarEspacios(clave);//Quitamos los espacios en blanco de la clave.
        
        mensaje=mensaje.toUpperCase();//Convertimos a mayúsculas el mensaje.
        clave=clave.toUpperCase();//Convertimos a mayúsculas la clave.
        
        clave=completar(clave,mensaje.length());//Ajustamos el tamaño de la clave al del mensaje.
        
        //aplicamos el algoritmo de Vigenere
        for(int i=0;i<mensaje.length();i++){//Realización de la intersección para obtener el valor de la matriz de vigenere para cada uno de los caracteres del mensaje.
        	
            criptograma+=getCaracter(mensaje.charAt(i),clave.charAt(i));//Producto de las intersecciones.
        }       
        
        return criptograma;
    }
    
    //MÉTODO PARA OBTENER LOS VALORES DE LA MATRIZ PARA EL DESCIFRADO
    char valorDescifrado(char criptogramaCifrado,char clave){
    	//Buscar en las columnas el argumento del mensaje y obtenemos su posición, buscar en las filas el argumento clave y obtenemos su posici�n en base, a partir de la intersecci�n de valores identificamos el nuevo caracter y se devuelve.
        int posicionMensaje=0;//Variable auxiliar para el valor de la columna de la matriz.
        int posicionClave=0;//Variable auxiliar para el valor de la fila de la matriz.
        int indice=0;//Valor de la intersección entre la fila de la clave y la columna del mensaje. 
        boolean encontrado=false;
        
       
        while(indice<26 && encontrado==false){
        	//Algoritmo de búsqueda empezando por las columnas.
            if(matriz[0][indice]==clave){
            	
                encontrado=true;
                posicionClave=indice;
            }
            
            indice++;
        }
        encontrado=false;
        indice=0;
 
        while(indice<26 && encontrado==false){
        	//Algoritmo de búsqueda para las filas.
            if (matriz[indice][posicionClave]==criptogramaCifrado){
            
                encontrado=true;
                posicionMensaje=indice;
            }
            indice++;
        }
        
        return matriz[0][posicionMensaje];
    }
 
    //FUNCIÓN DE DESCIFRAR
    public String descifrar(String mensaje,String clave){
    	//Cifra una cadena usando la clave con el algoritmo de vigenere.
    	criptograma=" ";
        
        mensaje=quitarEspacios(mensaje);//Quitamos espacios en blanco del mensaje.
        clave=quitarEspacios(clave);//Quitamos espacios en blanco de la clave.
        
        mensaje=mensaje.toUpperCase();//Convertimos a mayúculas el mensaje.
        clave=clave.toUpperCase();//Convertimos a mayúsculas la clave.
        
        clave=completar(clave,mensaje.length());//Ajustamos el tamaño de la clave al mensaje.

        for(int i=0;i<mensaje.length();i++){
        	//Obtenemos el valor de la matriz de vigenere como intersección entre la columna del mensaje y la fila de la clave y concatenamos el criptograma.
            criptograma+=valorDescifrado(mensaje.charAt(i),clave.charAt(i));
        }
        
        return criptograma;
    }
    
}