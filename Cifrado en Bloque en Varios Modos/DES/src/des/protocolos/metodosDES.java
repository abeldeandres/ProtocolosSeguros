package des.protocolos;

/* DES es un esquema de cifrado de bloque que opera sobre bloques de texto de 64 bits,
 * devolviendo bloques cifrados también de 64 bits. Así pues, DES sobre 2^64 posibles combinaciones de bits.
 * Cada bloque de 64 bits es dividido en dos bloques del mismo tamaño (32 bits cada uno).
 */

public class metodosDES {
    
    static final int TAMAÑO_UTIL_CLAVE=56;
    static final int TAMAÑO_CLAVE=64;
    static final int NUMEROCLAVES=16; //Nombre de las claves generadas.
    static final int TAMAÑO_CLAVES_GENERADAS=48;
    static final int TAMAÑO_TABLA_PERM=8*8; //Medida de la tabla de permutación de salida, filas*columnas.
    static final int TAMAÑO_TABLA_PERM_ENTRADA=8*8; //Medida de la tabla de permutación de entrada, filas*columnas.
    
    public void metodoPrincipal(String claveEntrada,String mensajeEntrada){//Parámetros del constructor.
    
        System.out.println("HEXADECIMAL MENSAJE:"+mensajeEntrada);
        System.out.println("HEXADECIMAL CLAVE:"+claveEntrada);
        String clave=claveEntrada;
        clave=hex_bin(clave);
        clave=AñadirCeros(clave);
        System.out.print("BINARIO CLAVE: "+clave +"\n"); 
        String mensaje=mensajeEntrada;
        mensaje=hex_bin(mensaje);
        mensaje=AñadirCeros(mensaje);
        System.out.print("BINARIO MENSAJE: "+mensaje +"\n");
   
        char[] charArray = clave.toCharArray();
        charArray=permutar(PERM_P1, charArray);;
        String Kmas = new String(charArray);
        String f0="";
        String g0="";
        f0=partirf0(Kmas);
        g0=partirg0(Kmas);
        g0.toCharArray();
        f0.toCharArray();
        
        char[][] keys = new char[16][48];
        keys=creacionClavesK(f0.toCharArray(),g0.toCharArray());   
        char[] clavesCifradas = cifrado(mensaje.toCharArray(), keys);
        System.out.println(clavesCifradas);
        char[] mensajeDescifrado=descifrado(clavesCifradas,keys);
        System.out.println(mensajeDescifrado);
        //Pasamos el resultado de binario a hexadecimal.
        String mensajeDescifradoBin = new String(mensajeDescifrado);
        mensajeDescifradoBin=bin_hex(mensajeDescifradoBin);
        System.out.println("DESCIFRADO HEXADECIMAL MENSAJE:"+mensajeDescifradoBin);
        mensajeDescifradoBin=HexAString(mensajeDescifradoBin);
        System.out.println("DESCIFRADO MENSAJE:"+mensajeDescifradoBin);
    
    }
    
    
    
    private String HexAString(String hex){
 
	  StringBuilder sb = new StringBuilder();
	  StringBuilder temp = new StringBuilder();
 
	  for( int i=0; i<hex.length()-1; i+=2 ){
 
	      //Cogemos los hexadecimales en parejas
	      String output = hex.substring(i, (i + 2));
	      //Convertimos el hexadecimal a decimal
	      int decimal = Integer.parseInt(output, 16);
	      //Convertimos el decimal a caracteres
	      sb.append((char)decimal);
 
	      temp.append(decimal);
	  }
	  //System.out.println("Decimal : " + temp.toString());
 
	  return sb.toString();
  }
    
    //METODO para pasar de hexadecimal a binario.
    private String hex_bin(String cadena){
    
        Long dec=Long.parseLong(cadena,16);
        cadena=Long.toBinaryString(dec);
        return cadena;
    }
    
    //METODO para pasar de binario a hexadecimal.
    private String bin_hex(String valor){
    
        long dec=Long.parseLong(valor,2);
        valor=Long.toHexString(dec);
        return valor;
    }
    
    //METODO para añadir ceros a la izquierda del texto en binario si no se llega a los 64 bits.
    private String AñadirCeros(String textoBinario){
   
        while (textoBinario.length()<64){
        
            textoBinario="0"+textoBinario;
        }
        return textoBinario;
    }
    
    /*
     * Cada bloque de 64 bits es dividido en dos bloques del mismo tamaño (32 bits cada uno), que se representan
mediante L (para los 32 bits de la izquierda) y R (para los 32 bits de mñs a la derecha).
     */
    
    //METODO para dividir el bloque en 32 bits de la izquierda.
    private String partirL (String textoBinario){
    
        String L="";
        
        for(int i=0;i<32;i++){
        
            L=L+textoBinario.charAt(i);
        }
            
        return L;
    }
    
    //METODO para dividir el bloque en 32 bits de la derecha.
    private String partirR (String textoBinario){
    
        String R="";
        
        for(int i=32;i<64;i++){
        
            R=R+textoBinario.charAt(i);
        }
            
        return R;
    }
    
    
    private char[] unirMitades(char[] parteIzquierda, char[] parteDerecha){
    	
		char[] r = new char[TAMAÑO_UTIL_CLAVE];
		
		for (int i=0; i<(TAMAÑO_UTIL_CLAVE/2); i++){ 
			r[i] = parteIzquierda[i]; 
		} 
		
		for (int j=0; j<(TAMAÑO_UTIL_CLAVE/2); j++) { 
			r[(TAMAÑO_UTIL_CLAVE/2) + j] = parteDerecha[j]; 
		}
		
		return r; 
	} 
    
    
    private char[] unirMitadesCifradoDescifrado(char[] parteIzquierda, char[] parteDerecha){ 
		char[] r = new char[SIZE_PS]; 
		
		for (int i=0; i<(SIZE_PS/2); i++){ 
			r[i] = parteIzquierda[i]; 
		} 
		
		for (int j=0; j<(SIZE_PS/2); j++){ 
			r[(SIZE_PS/2) + j] = parteDerecha[j]; 
		} 
		
		return r; 
	}
    
    static final int PERM_P1[] = {//Posición de los bits de la primera permutación.
    	/*
    	 * La clave de 64 bits se permuta usando la tabla posterior. Ya que la primera entrada en la tabla es 57, esto
    	 * significa que el bit nº 57 de la clave original pasará a ser el primer bit de la permutación; análogamente, 
    	 * el bit 49 pasaró a ser el segundo en la permutación, y así sucesivamente.
    	 */
    	
		57, 49, 41, 33, 25, 17, 9, 
		1, 58, 50, 42, 34, 26, 18, 
		10, 2, 59, 51, 43, 35, 27, 
		19, 11, 3, 60, 52, 44, 36, 
		63, 55, 47, 39, 31, 23, 15, 
		7, 62, 54, 46, 38, 30, 22, 
		14, 6, 61, 53, 45, 37, 29, 
		21, 13, 5, 28, 20, 12, 4}; 
    
    static final int PERM_P2[] = {//Posición de los bits de la segunda permutación.
    	
    	/*
    	 * generar las 16 sub-claves ki para nuestro esquema. Para ello, usaremos la permutación 
    	 * prefijada por la tabla PERM_P2 a cada par concatenado. Cada par contiene 
    	 * 56 bits; la permutación con PERM_P2 la reduce a 48 bits.
    	 */
    	
		14, 17, 11, 24, 1, 5, 
		3, 28, 15, 6, 21, 10, 
		23, 19, 12, 4, 26, 8, 
		16, 7, 27, 20, 13, 2, 
		41, 52, 31, 37, 47, 55, 
		30, 40, 51, 45, 33, 48, 
		44, 49, 39, 56, 34, 53, 
		46, 42, 50, 36, 29, 32}; 
    
    static final int SIZE_IP=8*8;//Medida de la tabla de permutación de entrada, filas*columnas. 
    
    //Para el cifrado del mensaje.
    static final int PERM_IP[] = {//Posición de los bits de la permutación de entrada.
    	
    	/*
    	 * Se realiza una permutación inicial, con la tabla IP sobre el mensaje en claro. Esto provoca una
    	 * reordenación de los bits originales como sigue:
    	 */
    	
		58, 50, 42, 34, 26, 18, 10, 2, 
		60, 52, 44, 36, 28, 20, 12, 4, 
		62, 54, 46, 38, 30, 22, 14, 6, 
		64, 56, 48, 40, 32, 24, 16, 8, 
		57, 49, 41, 33, 25, 17, 9, 1, 
		59, 51, 43, 35, 27, 19, 11, 3, 
		61, 53, 45, 37, 29, 21, 13, 5, 
		63, 55, 47, 39, 31, 23, 15, 7};
    
    //Expansión de cada bloque de 32 bits.
    static final int PERM_PL[] = {//Posición de los bits de la permutación.
    	
    	/*
    	 * Expandir de cada bloque de 32 bits a 48 bits (para poder operar con la clave). Esto se hará usando la tabla PL, 
    	 * que se muestra a continuación:
    	 * De esta forma, se obtiene una extensión de 48 bits. El resultado de la extensión definida por la tabla PL se
    	 * suele escribir como 8 bloques de 6 bits.
    	 */
    	
		32, 1, 2, 3, 4, 5, 
		4, 5, 6, 7, 8, 9, 
		8, 9, 10, 11, 12, 13, 
		12, 13, 14, 15, 16, 17, 
		16, 17, 18, 19, 20, 21, 
		20, 21, 22, 23, 24, 25, 
		24, 25, 26, 27, 28, 29, 
		28, 29, 30, 31, 32, 1};
    
    static final int SIZE_PS=8*8;
    
    //Inversa de la permutación inicial.
    static final int PERM_PS[] = {//Posición de los bits de la permutación de salida. 
    	
		40, 8, 48, 16, 56, 24, 64, 32, 
		39, 7, 47, 15, 55, 23, 63, 31, 
		38, 6, 46, 14, 54, 22, 62, 30, 
		37, 5, 45, 13, 53, 21, 61, 29, 
		36, 4, 44, 12, 52, 20, 60, 28, 
		35, 3, 43, 11, 51, 19, 59, 27, 
		34, 2, 42, 10, 50, 18, 58, 26, 
		33, 1, 41, 9, 49, 17, 57, 25}; 
    
	static final int PERM_PP[] = {//Posición de los bits de la permutación.
			16, 7, 20, 21, 
			29, 12, 28, 17, 
			1, 15, 23, 26, 
			5, 18, 31, 10, 
			2, 8, 24, 14, 
			32, 27, 3, 9, 
			19, 13, 30, 6, 
			22, 11, 4, 25}; 

	static final int CAJAS[] = { 
		
		/*
		 * En este punto, seguimos manejando bloques de 48 bits, más concretamente, tenemos 8 bloques de 6 bits
		 * cada uno. Cada uno de estos bloques nos servirá para indexar direcciones de entrada a un grupo de tablas
		 * de sustitución llamadas S-Cajas. Cada grupo de 6 bits nos dará una dirección diferente para la entrada de
		 * una S-Caja. En la dirección concreta que indique el bloque habrá un número de 4 bits, que será la salida
		 * de la S-Caja. Existirán un total de 8 S-Cajas (una para cada bloque). De esta forma la salida de este
		 * proceso convertirá 8 bloques de 6 bits en 8 bloques de 4 bits (32 bits en total).
		 * Las tablas que determinan estas sustituciones son las siguientes:
		 */
		
			14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, //S1 
			0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 
			4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 
			15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13, 
			15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, //S2 
			3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 
			0, 14, 7, 11, 10, 4, 12, 1, 5, 8, 12, 6, 9, 3, 2, 15, 
			13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9, 
			10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, //S3 
			13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 
			13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 
			1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12, 
			7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, //S4 
			13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 
			10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 
			3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14, 
			2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, //S5 
			14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 
			4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 
			11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3, 
			12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, //S6 
			10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 
			9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 
			4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13, 
			4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, //S7 
			13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 
			1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 
			6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12, 
			13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, //S8 
			1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 
			7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 
			2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}; 
    
	//Tabla de rotaciones (circulares de derecha a izquierda) prefijadas de la siguiente forma:
    static final int DC[] = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1}; 
 
    
     private char[] permutar (int tbP[], char tablaEnt[]){ 
		
    	 char[] tablaPerm = new char[tbP.length]; 
		
		for (int i=0; i<tbP.length; i++){
			
			tablaPerm[i]=tablaEnt[(tbP[i] - 1)]; 
		}
		
		return tablaPerm; 
	} 
    
     private String partirf0 (String textoBinario){
    
        String f0="";
        
        for(int i=0;i<28;i++){
        
            f0=f0+textoBinario.charAt(i);
        }
            
        return f0;
    }
    
    private String partirg0 (String textoBinario){
    
        String g0="";
        
        for(int i=28;i<56;i++){
        
            g0=g0+textoBinario.charAt(i);
        }
            
        return g0;
    }
    
    private char[] desplazarIzquierda(char[] cadena){ 
		int i;
		
		char[] r = new char[TAMAÑO_UTIL_CLAVE/2];
		
		for (i=1; i<(TAMAÑO_UTIL_CLAVE/2); i++){ 
			r[i-1] = cadena[i]; 
		} 
		
		r[i-1] = cadena[0]; 
		return r; 
	} 
    
    private char[][] creacionClavesK (char[] f0,char[]g0){
    
        char[][] keys = new char[NUMEROCLAVES][TAMAÑO_CLAVES_GENERADAS]; 
        
        char[] f1, g1;
        
        for (int i=0; i<16; i++){ 
        	
			f1 = desplazarIzquierda(f0); //Desplazamiento circular hacia la izquierda.
			g1 = desplazarIzquierda(g0); 
			
			if (DC[i] == 2) {//Doble desplazamiento.
				
				f1 = desplazarIzquierda(f1); 
				g1 = desplazarIzquierda(g1); 
			}
			
			char[] k = permutar(PERM_P2, unirMitades(f1, g1));//Segunda permutación.
			
			keys[i] = k; 
			f0 = f1; g0 = g1; 
		}
        
        return keys;
    }
    
    private char[] creaMitad (char[] cadena, char ED){ 
		int des = 0; 
		
		char[] r = new char[TAMAÑO_TABLA_PERM_ENTRADA/2];
		
		if (ED == 'D') des = TAMAÑO_TABLA_PERM_ENTRADA/2;
		
		for (int i=0; i<(TAMAÑO_TABLA_PERM_ENTRADA/2); i++) { 
			r[i] = cadena[des + i]; 
		} 
		
		return r; 
	} 
    
    
    private char[] XOR (char[] numBin1, char[] numBin2){ 
    	
		char[] res = new char[numBin1.length]; 
		
		for (int i=0; i<numBin1.length; i++) { 
			if (numBin1[i] == numBin2[i]) res[i] = '0'; else res[i] = '1'; 
		} 
		return res; 
	} 
   
    private char[] cifrado(char[] textChBin, char[][]claves){ 
		int loopini, loopend, loopinc;
		loopini = 0;
		loopend = NUMEROCLAVES; 
		loopinc = 1; 	
		char[] pe = permutar(PERM_IP, textChBin); //Permutación de entrada.
		char[] f; 
		char[] e1 = new char[SIZE_PS/2]; 
		char[] d1 = new char[SIZE_PS/2]; 
		char[] e0 = creaMitad(pe, 'I'); //Dos mitades iniciales.
		char[] d0 = creaMitad(pe, 'D'); 
		
		for (int i=loopini; i!=loopend; i+=loopinc){ 
			e1 = d0; 
			f = funcioF(i+1, d0, claves[i]); 
			d1 = XOR(e0, f); 
			e0 = e1; d0 = d1; 
		} 
		
		char[] ps = permutar(PERM_PS, unirMitadesCifradoDescifrado(d1, e1)); //Permutación de salida.
		return ps; 
	} 
    
    private char[] descifrado(char[] textChBin, char[][]claves){ 
		int loopini, loopend, loopinc; 		
		loopini = NUMEROCLAVES -1; 
		loopend = -1; 
		loopinc = -1; 		
		char[] pe = permutar(PERM_IP, textChBin); //Permutación de entrada.
		char[] f; 
		char[] e1 = new char[SIZE_PS/2]; 
		char[] d1 = new char[SIZE_PS/2]; 
		char[] e0 = creaMitad(pe, 'I'); //Dos mitades iniciales.
		char[] d0 = creaMitad(pe, 'D'); 
		
		for (int i=loopini; i!=loopend; i+=loopinc){ 
			e1 = d0; 
			f = funcioF(i+1, d0, claves[i]); 
			d1 = XOR(e0, f); 
			e0 = e1; d0 = d1; 
		}
		
		char[] ps = permutar(PERM_PS, unirMitadesCifradoDescifrado(d1, e1)); //Permutación de salida.
		return ps; 
	} 
    
    static final int NUMCAJAS=8; 
    static final int BITS_E_CAJA=6; 
    static final int BITS_S_CAJA=4;
    private char[] funcioCaixa (char[] blocEnt){ 
		int rcaja, fila, columna; 
		char[] r = new char[BITS_S_CAJA * NUMCAJAS]; 
		char[] rc = new char[BITS_S_CAJA]; 
		char bloc[] = new char [BITS_E_CAJA]; 
		
		for (int i=0; i<NUMCAJAS; i++) { 
			for (int j=0; j<BITS_E_CAJA; j++) bloc[j] = blocEnt[(i*BITS_E_CAJA)+j]; 
			fila = binToDec('0', '0', bloc[0], bloc[5]); 
			columna = binToDec(bloc[1], bloc[2], bloc[3], bloc[4]); 
			rcaja = CAJAS[(i*64) + (fila*16) + columna]; 
			rc = decToBin(rcaja).toCharArray(); 
			for (int k=0; k<BITS_S_CAJA; k++) r[(i*BITS_S_CAJA)+k] = rc[k]; 
		} 
		
		return r; 
	} 
    
    private char[] funcioF (int ordre, char[] blocD, char[] subclau){ 
		char[] l = permutar(PERM_PL, blocD); 
		char[] entCaixa = XOR(l, subclau); 
		char[] salCaixa = funcioCaixa(entCaixa); 
		char[] r = permutar(PERM_PP, salCaixa);  
	
		return r; 
	} 
    
    private int binToDec(char a, char b, char c, char d){
    	
		if (a == '0' && b == '0' && c == '0' && d == '0') return 0; 
		else if (a == '0' && b == '0' && c == '0' && d == '1') return 1; 
		else if (a == '0' && b == '0' && c == '1' && d == '0') return 2; 
		else if (a == '0' && b == '0' && c == '1' && d == '1') return 3; 
		else if (a == '0' && b == '1' && c == '0' && d == '0') return 4; 
		else if (a == '0' && b == '1' && c == '0' && d == '1') return 5; 
		else if (a == '0' && b == '1' && c == '1' && d == '0') return 6; 
		else if (a == '0' && b == '1' && c == '1' && d == '1') return 7; 
		else if (a == '1' && b == '0' && c == '0' && d == '0') return 8; 
		else if (a == '1' && b == '0' && c == '0' && d == '1') return 9; 
		else if (a == '1' && b == '0' && c == '1' && d == '0') return 10; 
		else if (a == '1' && b == '0' && c == '1' && d == '1') return 11; 
		else if (a == '1' && b == '1' && c == '0' && d == '0') return 12; 
		else if (a == '1' && b == '1' && c == '0' && d == '1') return 13; 
		else if (a == '1' && b == '1' && c == '1' && d == '0') return 14; 
		
		else return 15; 
	} 

	private String decToBin(int numDec){ //Convertir decimal en binario.
		switch(numDec){
		case 0: return "0000"; 
		case 1: return "0001"; 
		case 2: return "0010"; 
		case 3: return "0011"; 
		case 4: return "0100"; 
		case 5: return "0101"; 
		case 6: return "0110"; 
		case 7: return "0111"; 
		case 8: return "1000"; 
		case 9: return "1001"; 
		case 10: return "1010"; 
		case 11: return "1011"; 
		case 12: return "1100"; 
		case 13: return "1101"; 
		case 14: return "1110"; 
		case 15: return "1111"; 
		} 
		
		return ""; 
	} 

}
