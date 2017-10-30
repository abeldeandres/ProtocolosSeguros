/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg1;

/**
 *
 * @author abelillo
 */
public class metodosSHA1 {
    //En el metodo general llamaremos a todos los metodos de la propia clase.
    public void metodoGeneral(String frase)
    {
        String mensajeFinalizado="";
        int tamañoMensajeInicial=pasarStringBinario(frase).length();
        mensajeFinalizado=pasarStringBinario(frase);
        mensajeFinalizado=AñadirCeros(mensajeFinalizado);
        mensajeFinalizado=longitudMensajeABinario(mensajeFinalizado, tamañoMensajeInicial);
        String[] chunk32 = new String[80];
        chunk32=dividirChunk32(mensajeFinalizado);
       /* for(int i=0; i<80;i++)
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
            resultado_final=resultado_final+Long.toHexString(Long.parseLong(StringH[i],2));
        }
        System.out.println(resultado_final);
        
        
    }
    //Este metodo le utilizaremos para pasar un String a binario
    public String pasarStringBinario(String frase)
    {
         String textoBinario = ""; 
	        for(char letra : frase.toCharArray()) //Recorreremos el string frase convirtiendo cada letra a binario
	        {
	            textoBinario += String.format("%8s", Integer.toBinaryString(letra));
	        }
                textoBinario= textoBinario.replace("\u0020","\u0030"); //Lo que se hace aqui es reemplazar el carater de espacio, por un 0
                //El SHA-1 NOS pide que añadamos un 1 al final.
                //textoBinario=textoBinario+"1";
	        return textoBinario; 
    }
    
    public String AñadirCeros(String textoBinario)
    {
        //Tenemos que añadir ceros hasta llegar a 512.
        int longitudMensaje=textoBinario.length();
        //El SHA-1 NOS pide que añadamos un 1 al final.
        textoBinario=textoBinario+"1";
        //Por lo tanto a la longitud del mensaje original le añadimos 0s hasta llegar a 512 digitos despues de haber añadido el 1
        int numCeros=((((448-longitudMensaje)% 512) + 512) % 512); 
        for (int i=0;i<numCeros;i++)
        {
            textoBinario=textoBinario+"0";
        }
        return textoBinario;
        
    }
    //El SHA-1 nos pide que al final del mensaje se le añada la longitud de este de forma binaria, por lo tanto
    //en este metodo introduciremos el mensaje y la longitud de este y le añadiremos esa longitud al final
    //Primero pasaremos la longitud del mensaje a binario
    //Despues lo añadiremos al final
    public String longitudMensajeABinario(String textoBinario,int longitudMensaje)
    {
        String longitudEnBin=Integer.toBinaryString(longitudMensaje);
        int longitudEnBinInt=longitudEnBin.length();
        int resto;
        resto=64-longitudEnBinInt; //La longitud del mensaje como maximo puede tener 64 bits por lo tanto calcularemos 
        //el numero de ceros que la longitud en binario tendra delante. Por ejemplo si la longitud del mensaje fuese de 48, 
        //48 en binario es 110000 hasta llegar a los 64 bits hay 64-6=58 ceros delante eso lo calcularemos a continuacion
        for (int i=0;i<resto-1;i++)
        {
            textoBinario=textoBinario+"0";
        }
        //Una vez que tenemos un string lleno de ceros, si seguimos el ejemplo anterior tendriamos un string de 58 ceros
        //Ahora se lo añadimos delante a la longitud del mensaje original en bits.
        textoBinario=textoBinario+longitudEnBin;           
        return textoBinario;
    }
    //Otro paso de SHA-1 es dividir la cadena que obtuvimos anteriormente de 512 bits en 16 cadenas de 32 bits
    public String[] dividirChunk32(String textoBinario)
    {
        String[] chunk32 = new String[80]; 
        //char[] ArrayChar = textoBinario.toCharArray();
        int contador=0; 
        //Ahora vamos a crear los 16 primeros trozos cada uno de 32 bits ya se ha comentado utilizaremos
        //un bucle for del 0 al 15 para que nos produzca estos trozos y cada trozo contara del 0 al 32, del 32 al 64, etc... 
        //de la cadena original y lo metera al array de chunk32
        for(int j=0;j<16;j++)
       {
           chunk32[j]=textoBinario.substring(contador, contador+32);
           contador=contador+32;
       }
        //Ya tenemos los 16 primeros trozos, ahora necesitaremos los 64 trozos restantes
        //hasta llegar al 79. Los trozos restantes se crearan mediante un algoritmo de SHA-1
        //Este algoritmo se basara en hacer XOR de los trozos anteriores y los trozos a elegir 
        //son seleccionados mediante el algoritmo [i-3], [i-8], [i-14] e [i-16]
        //En el primer caso p=16 haremos un XOR del trozo 16-3=13 y del trozo 16-8=8
        //Este trozo formado le haremos un XOR con el trozo 16-14=2
        //Este nuevo trozo formado le haremos un XOR con el trozo 16-16=0;
        //Este nuevo trozo formado sera el trozo 16
        //Asi continuara hasta que consigamos los bloques restantes para llegar al 79.
        for(int p=16;p<80;p++)
        {
            String palabra;
            palabra=XOR(chunk32[p-3],chunk32[p-8]);
            palabra=XOR(palabra,chunk32[p-14]);
            palabra=XOR(palabra,chunk32[p-16]);
            palabra=palabra.substring(1,palabra.length())+palabra.charAt(0); //añadimos un cero al final
            chunk32[p]=palabra;
        }
        
        
        /*for(int i=0; i<80;i++)
        {
          System.out.println(chunk32[i]);  
        }*/
 
       //Ahora vamos a devolver un array de strings donde tendremos los 80 trozos formados.
       return chunk32;
    }
    //En esta funcion realizaremos el XOR de dos cadenas introducidas
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
     //En esta funcion realizaremos el AND de dos cadenas introducidas
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
     //En esta funcion realizaremos el OR de dos cadenas introducidas
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
    //En esta funcion realizaremos el NOT de la cadena introducida.
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
     //Aqui le pasamos de nuevo el array de chunk que creamos anteriormente y que
    //contenia los 80 trozos formados.
    //Ahora a cada trozo le aplicaremos diferentes algoritmos
     public String[] funciones (String[] array)
     {
         //Tenemos unas constantes del SHA-1 que son h0,h1,h2,...,h4 y las utilizaremos con las
         //funciones que vendran a continuacion.
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
         //Los pasos de cada funcion vienen determinados en los apuntes y son generales
         //para el desarrollo del SHA-1. No es necesario comentar los pasos
         //Es necesario comentar que por ejemplo desde los trozos 0 hasta los 19 se va a aplicar
         //la funcion 1, desde los 20 hasta los 40 se va a aplicar la funcion 2, desde los 40 hasta los 
         //60 se va a aplicar la funcion 3 y desde los 60 a los 80 se va a aplicar la funcion 4
         //En cada funcion modificaremos los parametros de A,B,C,D,E, Temp,F y K al final estos parametros
         //seran utilizados para crear el hash durante todo el proceso se iran modificando.
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
        //Una vez que tenemos A,B,C,D y E se los sumaremos a las funciones cosntantes habiendolos pasado
         //a decimal es decir, pasamos todo a decimal y lo sumamos.
         palabrasFinales[0]=DecABin(BinADec(h0)+BinADec(A));
         palabrasFinales[1]=DecABin(BinADec(h1)+BinADec(B));
         palabrasFinales[2]=DecABin(BinADec(h2)+BinADec(C));
         palabrasFinales[3]=DecABin(BinADec(h3)+BinADec(D));
         palabrasFinales[4]=DecABin(BinADec(h4)+BinADec(E));

         return palabrasFinales;
         
     }
     
     //La funcion rotar nos rotara una cadena tantas veces como el numero que indicamos de esta forma (hacia la izquierda)
     //JAVA
     //AVAJ
     //VAJA
     //AJAV
     //JAVA
     //En el ejemplo anterior rotamos 4 veces.
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
     //Con esta funcion vamos a pasar de binario a decimal cualquier string que tengamos
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
      //Con esta funcion vamos a pasar de decimal a binario cualquier string que tengamos
     public String DecABin(long numero)
     {
         return Long.toBinaryString(numero);
     }
     //En caso de que el string sea mayor de 32 se reducira a 32 seleccionando
     //los 32 bits de derecha a izquierda del trozo
     //Si el string es igual a 32 no se hara nada
     //Si el string es menor que 32 se añadiran 0s a la izquierda
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