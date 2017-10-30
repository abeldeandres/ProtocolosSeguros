
import dsa.DSASHA1;
import java.math.BigInteger;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.KeySpec;
import java.util.Random;
import java.util.Scanner;




public class Main {
    
    /* Integer x = 5;
      System.out.println(x.compareTo(3));  1
      System.out.println(x.compareTo(5));  0
      System.out.println(x.compareTo(8)); -1*/
    //Metodo para crear un numero aleatorio entre 1<x<q
    public static BigInteger numAleatorioGrande(BigInteger n) {
    Random rand = new Random();
    BigInteger result = new BigInteger(n.bitLength(), rand);
    BigInteger uno= new BigInteger("1");
    while( (result.compareTo(n) != -1) && (result.compareTo(uno) !=1) ) {
        result = new BigInteger(n.bitLength(), rand);
    }
    return result;
}
    //Metodo para pasar de binario a decimal
    public static long BinADec(String cadena)
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
    //Metodo para implementar la potencia de dos numeros muy grandes -->BigInteger
    public static BigInteger pow(BigInteger base, BigInteger exponent) {
    BigInteger result = BigInteger.ONE;
    while (exponent.signum() > 0) {
    if (exponent.testBit(0)) result = result.multiply(base);
    base = base.multiply(base);
    exponent = exponent.shiftRight(1);
  }
  return result;
}
    //Metodo para la exponenciacion modular. Aunque hay que destacar que no es necesario 
    //puesto que al final nos dimos cuenta que JAVA permite la exponenciacionn modular.
    private static BigInteger modulo(BigInteger a, BigInteger b, BigInteger c) { 

    BigInteger x = BigInteger.ONE;
    final BigInteger TWO = new BigInteger("2", 16);

    while(b.compareTo(BigInteger.ZERO) > 0) {

        BigInteger compareVal = b.mod(TWO);
        if(compareVal.compareTo(BigInteger.ONE) ==0) { 
            x = (x.multiply(a)).mod(c);
        }

        a = (a.multiply(a)).mod(c);
        b = b.divide(TWO);
    }
    return x.mod(c);

}
    
    
    

  public static void main(String[] argv) throws Exception {
    //Convertir mensaje a Hash con Sha-1
    Scanner sc = new Scanner(System.in);
    DSASHA1 metodos = new DSASHA1();
    System.out.println("Introduzca el Mensaje");
    String texto=sc.nextLine();
    texto=metodos.metodoGeneral(texto); //Obtenemos el hash en binario utilizando SHA-1 ya implementado
    long decimal=BinADec(texto); //Pasamos el binario a decimal
    BigInteger HM= BigInteger.valueOf(decimal); //Pasamos el decimal a BigInteger
    System.out.println("HM"+HM);//Mostramos el mensaje en Hash
    
    //JAVA permite mediante la clase JAVA SECURITY la generacion aleatoria de ciertos parametros
    //introduciendo los parametros dichos. En este caso le decimos que utilizaremos el DSA, pero podria 
    //generar las claves para RSA por ejemplo
     KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
    keyGen.initialize(1024);//Indicamos la longitud de la clave
    //Este codigo esta sacado de la API de JAVA, pero a simple vista se puede comprobar que vamos 
    //generando todas las claves que se nos pide para DSA (p,q,g,y) X es la clave privada 
    KeyPair keypair = keyGen.genKeyPair();
    DSAPrivateKey privateKey = (DSAPrivateKey) keypair.getPrivate();
    DSAPublicKey publicKey = (DSAPublicKey) keypair.getPublic();
    DSAParams dsaParams = privateKey.getParams();
    //Obtenemos parametro a parametro generado aleatoriamente.
    BigInteger p = dsaParams.getP();
    BigInteger q = dsaParams.getQ();
    BigInteger g = dsaParams.getG();
    BigInteger x = privateKey.getX();
    BigInteger y = publicKey.getY();
      
      System.out.println("p"+p);
      System.out.println("q"+q);
      System.out.println("g"+g);
      System.out.println("x"+x);
      System.out.println("y"+y);
 

   
      //Firma 
      BigInteger k= numAleatorioGrande(p); //Elegimos un número aleatorio k, donde 1 < k < q.
      //Calculamos r = (gk mod p)mod q.
      BigInteger r=modulo(g,k,p); 
      r=r.mod(q);
      System.out.println("r"+r);//funciona
      
      
      //Calculamos s = k^-1(H(m)+r*x) mod q H(m) seria la funcion Hash del mensaje.
      BigInteger aux;
      aux=HM.add(r.multiply(x));//calculamos H(m)+r*x
      System.out.println(aux);
      BigInteger inversaK=k.modInverse(q); //Ahora calculamos la inversa de k mod q
      
      System.out.println(inversaK);
      BigInteger s;
      s=inversaK.multiply(aux);//Multiplicamos la inversa de k mod q por el valor de H(m)+r*x
      s=s.mod(q);
      System.out.println("s"+s); //Mostramos s
      
      //La Firma seria el par (r,s) obtenido anteriormente
       
      //Verificar firma
     //Calculamos w = (s)-1(mod q).
      BigInteger w;
      w=s.modInverse(q);
      System.out.println("w"+w);//Mostramos W
      
      //Calculamos u1 = H(m)*w(mod q)
      BigInteger aux3;
      aux3=HM.multiply(w); //Calculamos H(m)*w
     // System.out.println("aux3"+aux3);
      BigInteger u1;
      u1=aux3.mod(q); //Realizamos toda la operacion H(m)*w (mod q)
      //System.out.println("u1"+u1);//funciona
      
    //Calculamos u2 = r*w(mod q).
      BigInteger aux4;
      aux4=r.multiply(w);
      //System.out.println("aux4"+aux4);
      BigInteger u2;
      u2=aux4.mod(q);
      //System.out.println("u2"+u2);//funciona
      
     
       
       //Calculamos v = [gu1*yu2mod p] mod q.
       //Verifiquemos: (A * B) mod C = (A mod C * B mod C) mod C
      //Puesto que JAVA no permite hacer directamente (A * B) mod C o no hemos detectado
      //ningun metodo, hemos realizado otras operaciones semejante mediante las propiedades de la
      //exponenciacion modular --> (A mod C * B mod C) mod C
      //Creamos auxiliares que nos ayudaran con las operaciones
       BigInteger auxiliar1;
       BigInteger auxiliar2;
       BigInteger auxiliar3;
       auxiliar1=g.modPow(u1, p); //g^u1 mod p
       auxiliar2=y.modPow(u2, p); //y^u2 mod p
       auxiliar3=auxiliar1.multiply(auxiliar2);  //(g^u1 mod p) * (y^u2 mod p)
       auxiliar3=auxiliar3.mod(p); // (g^u1 mod p) * (y^u2 mod p) mod p
       //Definitivamente calculamos V haciendo modulo q de auxiliar3
       BigInteger v;
       v=auxiliar3.mod(q);
       System.out.println("v"+v);
      
      if (v.compareTo(r)==0)
      {
          System.out.println("LA FIRMA ES CORRECTA");
      }
      else System.out.println("LA FIRMA NO ES CORRECTA");
      
      //Para que la firma fuera incorrecta, simplemente con modificar el par (r,s) seria suficiente
      //Obviamente para que todo sea correcto, el emisor es quien dice ser, por lo tanto se autentica
      //Si una persona se hiciera pasar por el emisor, se detectaria ya que no podria obtener (s) porque no
      //tendria la clave privada X
      
  }

    
}
