
public class RC4 {
 
    private char[] key; //Declaramos la key como un array de caracteres
    private int[] sbox; //Declaramos la caja S como un array de enteros
    private static final int SBOX_LENGTH = 256; //Limitamos el tamaño de la caja a 256 enteros
    private static final int KEY_MIN_LENGTH = 5; //Limitamos la longitud mínima de la key a 5 enteros
 
    public static void main(String[] args) {

            RC4 rc4 = new RC4("1234"); //Aquí se declara la key
            char[] result = rc4.encrypt("6789".toCharArray()); //Aquí se declara el mensaje
            System.out.println("Mensaje encriptado -  " + new String(result)); //Muestra por pantalla la cadena encriptada
            System.out.println("Mensaje desencriptado -  " + new String(rc4.decrypt(result))); //Muestra por pantalla la cadena desencriptada
        
    }
 
    public RC4(String key) {
        setKey(key);
    } //Método constructor de la clase RC4 con el argumento key 
 
    public RC4() {
    } //Método constructor de la clase RC4 sin argumentos
 
    public char[] decrypt(final char[] msg) {
        return encrypt(msg);
    } //Algoritmo para descifrar
 
    public char[] encrypt(final char[] msg) {
        sbox = initSBox(key);
        char[] code = new char[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (char) (rand ^ (int) msg[n]);
        }
        return code;
    } //Algoritmo para cifrar
 
    private int[] initSBox(char[] key) {
        int[] sbox = new int[SBOX_LENGTH]; //Especificamos el tamaño de la caja S con SBOX_LENGTH (256)
        int j = 0;
 
        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i; //Inicializamos la caja S con el 0
        }
 
        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + key[i % key.length]) % SBOX_LENGTH;
            swap(i, j, sbox);
        }
        return sbox; //Devuelve la caja S
    }
 
    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    } //Este método intercambia las posiciones i y j de la caja S
 
    public void setKey(String key)  {
        if (!(key.length() >= KEY_MIN_LENGTH && key.length() < SBOX_LENGTH)) {
            
        }
 
        this.key = key.toCharArray();
    } //Este método controla que la longitud de la clave este comprendido entre la longitud mínima de la key (5) y el tamaño de la caja (256)
    
}


