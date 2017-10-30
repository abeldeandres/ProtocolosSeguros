/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsa;

/**
 *
 * @author abelillo
 */
public class DSASHA1 {
    
    public String metodoGeneral(String frase)
    {
        String mensajeFinalizado="";
        int tamañoMensajeInicial=pasarStringBinario(frase).length();
        mensajeFinalizado=pasarStringBinario(frase);
        mensajeFinalizado=AñadirCeros(mensajeFinalizado);
        mensajeFinalizado=longitudMensajeABinario(mensajeFinalizado, tamañoMensajeInicial);
        String[] chunk32 = new String[80];
        chunk32=dividirChunk32(mensajeFinalizado);
        /*for(int i=0; i<80;i++)
        {
          System.out.println(chunk32[i]);  
        }*/
        String[] StringH = new String[5];
        StringH=funciones(chunk32);
        
      for (int i=0; i<5; i++)  //Truncamos la salida
      {
          StringH[i]=ReducirA32(StringH[i]);
      }
      
       //Ahora vamos a pasar cada H0,H1,H2... final a hexadecimal.
        String resultado_final="";
        for (int i=0;i<5;i++)
        {
            resultado_final=resultado_final+StringH[i];
        }
        return resultado_final;
        
        
    }
    
    public String pasarStringBinario(String frase)
    {
         String textoBinario = "";
	        for(char letra : frase.toCharArray())
	        {
	            textoBinario += String.format("%8s", Integer.toBinaryString(letra));
	        }
                textoBinario= textoBinario.replace("\u0020","\u0030"); //Lo que se hace aqui es reemplazar el carater de espacio, por un 0
                //El SHA-1 NOS pide que añadamos un 1 al final.
                //textoBinario=textoBinario+"1";
	        return textoBinario; //Lo que se hace aqui es reemplazar el carater de espacio, por un 0
    }
    
    public String AñadirCeros(String textoBinario)
    {
        //Tenemos que añadir ceros hasta llegar a 512.
        int longitudMensaje=textoBinario.length();
        //El SHA-1 NOS pide que añadamos un 1 al final.
        textoBinario=textoBinario+"1";
        int numCeros=((((448-longitudMensaje)% 512) + 512) % 512);
        for (int i=0;i<numCeros;i++)
        {
            textoBinario=textoBinario+"0";
        }
        return textoBinario;
        
    }
    
    public String longitudMensajeABinario(String textoBinario,int longitudMensaje)
    {
        String longitudEnBin=Integer.toBinaryString(longitudMensaje);
        int longitudEnBinInt=longitudEnBin.length();
        int resto;
        resto=64-longitudEnBinInt;
        for (int i=0;i<resto-1;i++)
        {
            textoBinario=textoBinario+"0";
        }
        textoBinario=textoBinario+longitudEnBin;           
        return textoBinario;
    }
    
    public String[] dividirChunk32(String textoBinario)
    {
        String[] chunk32 = new String[80]; 
        //char[] ArrayChar = textoBinario.toCharArray();
        int contador=0; 
        for(int j=0;j<16;j++)
       {
           chunk32[j]=textoBinario.substring(contador, contador+32);
           contador=contador+32;
       }

        for(int p=16;p<80;p++)
        {
            String palabra;
            palabra=XOR(chunk32[p-3],chunk32[p-8]);
            palabra=XOR(palabra,chunk32[p-14]);
            palabra=XOR(palabra,chunk32[p-16]);
            palabra=palabra.substring(1,palabra.length())+palabra.charAt(0);
            chunk32[p]=palabra;
        }
        
        
        /*for(int i=0; i<80;i++)
        {
          System.out.println(chunk32[i]);  
        }*/
 
       
       return chunk32;
    }
    
     public String XOR(String cadenaA, String cadenaB) {
        String cadSalida = "";
        char[] charCadenaA = new char[cadenaA.length()];
        charCadenaA = cadenaA.toCharArray();
        char[] charCadenaB = new char[cadenaB.length()];
        charCadenaB = cadenaB.toCharArray();

        for (int i = 0; i < cadenaA.length(); i++) {
            if (charCadenaA[i] == charCadenaB[i]) {
                cadSalida = cadSalida + "0";
            } else {
                cadSalida = cadSalida + "1";
            }
        }

        return cadSalida;

    }
     
     public String AND(String cadenaA, String cadenaB) {
        String cadSalida = "";
        char[] charCadenaA = new char[cadenaA.length()];
        charCadenaA = cadenaA.toCharArray();
        char[] charCadenaB = new char[cadenaB.length()];
        charCadenaB = cadenaB.toCharArray();

        for (int i = 0; i < cadenaA.length(); i++) {
            if ((charCadenaA[i] == '1') && (charCadenaB[i] == '1')) {
                cadSalida = cadSalida + "1";
            } else {
                cadSalida = cadSalida + "0";
            }
        }
        return cadSalida;
    }
     
    public String OR(String cadenaA, String cadenaB) {
        String cadSalida = "";
        char[] charCadenaA = new char[cadenaA.length()];
        charCadenaA = cadenaA.toCharArray();
        char[] charCadenaB = new char[cadenaB.length()];
        charCadenaB = cadenaB.toCharArray();

        for (int i = 0; i < cadenaA.length(); i++) {
            if ((charCadenaA[i] == '1') || (charCadenaB[i] == '1')) {
                cadSalida = cadSalida + "1";
            } else {
                cadSalida = cadSalida + "0";
            }
        }
        return cadSalida;

    }
    
    public String NOT(String cadenaA) {
        String cadSalida = "";
        char[] charCadenaA = new char[cadenaA.length()];
        charCadenaA = cadenaA.toCharArray();

        for (int i = 0; i < cadenaA.length(); i++) {
            if ((charCadenaA[i] == '1')) {
                cadSalida = cadSalida + "0";
            } else {
                cadSalida = cadSalida + "1";
            }
        }
        return cadSalida;

    }
     
     public String[] funciones (String[] array)
     {
         String h0="01100111010001010010001100000001";
         String h1="11101111110011011010101110001001";
         String h2="10011000101110101101110011111110";
         String h3="00010000001100100101010001110110";
         String h4="11000011110100101110000111110000";
         String A=h0;
         String B=h1;
         String C=h2;
         String D=h3;
         String E=h4;
         String Temp;
         String F;
         String K;
         long suma;
         
         String[] palabrasFinales = new String[5];
         
         for (int i=0;i<20;i++)//Funcion1
         {
             K="01011010100000100111100110011001";
             F=OR(AND(B,C),AND(NOT(B),D));
             suma=BinADec(Rotar(A,5))+BinADec(F)+BinADec(E)+BinADec(K)+BinADec(array[i]);
             Temp=DecABin(suma);
             Temp=ReducirA32(Temp);
             E=D;
             D=C;
             C=Rotar(B,30);
             B=A;
             A=Temp;
             
         }
         
         for (int i=20;i<40;i++)//Funcion2
         {
            K="01101110110110011110101110100001";
            F=XOR(XOR(B,C),D);
            suma=BinADec(Rotar(A,5))+BinADec(F)+BinADec(E)+BinADec(K)+BinADec(array[i]);
            Temp=DecABin(suma);
            Temp=ReducirA32(Temp);
            E=D;
            D=C;
            C=Rotar(B,30);
            B=A;
            A=Temp;
         }
         
         for (int i=40;i<60;i++)//Funcion3
         {
            K="10001111000110111011110011011100";
            String AND1=AND(B,C);
            String AND2=AND(B,D);
            String AND3=AND(C,D);
            F=XOR(XOR(AND1,AND2),AND3);
            suma=BinADec(Rotar(A,5))+BinADec(F)+BinADec(E)+BinADec(K)+BinADec(array[i]);
            Temp=DecABin(suma);
            Temp=ReducirA32(Temp);
            E=D;
            D=C;
            C=Rotar(B,30);
            B=A;
            A=Temp;
         }
         
         for (int i=60;i<80;i++)//Funcion4
         {
            K="11001010011000101100000111010110";
            F=XOR(XOR(B,C),D);
            suma=BinADec(Rotar(A,5))+BinADec(F)+BinADec(E)+BinADec(K)+BinADec(array[i]);
            Temp=DecABin(suma);
            Temp=ReducirA32(Temp);
            E=D;
            D=C;
            C=Rotar(B,30);
            B=A;
            A=Temp;
         }
            /*h0= h0 + A
            h1 = h1 + B
            h2 = h2 + C
            h3 = h3 + D
            h4 = h4 + E*/
        
         palabrasFinales[0]=DecABin(BinADec(h0)+BinADec(A));
         palabrasFinales[1]=DecABin(BinADec(h1)+BinADec(B));
         palabrasFinales[2]=DecABin(BinADec(h2)+BinADec(C));
         palabrasFinales[3]=DecABin(BinADec(h3)+BinADec(D));
         palabrasFinales[4]=DecABin(BinADec(h4)+BinADec(E));

         return palabrasFinales;
         
     }
     
     
     public String Rotar(String frase, int numero)
     {
         if (numero==0)
         {
             return frase;
         }
                 
         for(int i=1;i<=numero;i++){

            frase=frase.substring(1,frase.length())+frase.charAt(0);
        }
         
         return frase;
     }
     
     public long BinADec(String cadena)
     {

        double decimal=0;
        int posicion=0;
        for (int i=cadena.length()-1; i>-1; i--)
        {
            if (cadena.charAt(i)=='1')
            {
                double potencia;
                int posInt=posicion;          
                potencia= Math.pow(2, posInt);
                decimal=decimal+potencia;        
            }
            posicion++;

        }
        long decimalLong=(long) decimal;
        return decimalLong;
     }
     
     public String DecABin(long numero)
     {
         return Long.toBinaryString(numero);
     }
     
     public String ReducirA32(String frase)
     {
         if (frase.length()==32)
         {
             return frase;
         }
         else if(frase.length()>32)
         {
             String fraseNueva="";
             int diferencia;
             diferencia=frase.length()-32;
             for(int i=diferencia;i<frase.length();i++)
             {
                 fraseNueva=fraseNueva+frase.charAt(i);
             }
             return fraseNueva;
             
         }
         else
         {
             String fraseNueva="";
             int diferencia;
             diferencia=32-frase.length();
             for(int j=0;j<diferencia;j++)
             {
                 fraseNueva=fraseNueva+"0";
             }
             
             for(int i=0;i<frase.length();i++)
             {
                 fraseNueva=fraseNueva+frase.charAt(i);
             }
             return fraseNueva;
         }
     }
}