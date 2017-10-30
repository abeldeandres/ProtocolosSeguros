/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado.cesar;

/**
 *
 * @author abelillo
 */
public class metodos {
    public String codificacion(String mensaje, String clave)
    {

        char [] mensajeCharArray = new char[mensaje.length()];
        clave=clave.toLowerCase(); //pasamos la clave a minusculas (nuestro alfabeto esta en minusculas)
        mensaje=mensaje.toLowerCase(); //pasamos el mensaje a minusculas (nuestro alfabeto esta en minusculas)
        mensajeCharArray=mensaje.toCharArray(); //pasamos el mensaje a un char array con el fin de poder trabajar letra por letra
        char letra=clave.charAt(0);//En caso de que el usuario introduzca mas de una letra, este cogera solo la primera
        String mensajecodificado="";
        //Ahora vamos a codificar letra por letra de nuestro mensaje utilizando el metodo codificacionLetra
        for(int i=0;i<mensaje.length();i++)
        {   
            mensajecodificado+=codificacionLetra(letra,mensajeCharArray[i]);
        }
        
        return mensajecodificado;
    }
    //El metodo de codificacion consiste en que nosotros obtendremos la letra de la clave y cada letra del mensaje
    //Sumaremos la posiciones de las letras y haremos el modulo 26 ya que tenemos 26 letras en el alfabeto.
    //Por ejemplo si nuestra clave es la "a" y nuestro mensaje es hola. La primera letra de hola es la "h" que ocupa 
    //la posicion 8 y la "a" ocupa la posicion 1. Esto significa que cada letra la rotaremos una posicion por lo tanto la 
    //h sera la i, la o sera la p, la l sera la m y la a sera la b. En caso de que seleccionasemos como clave la b se rotaria 2 letras, etc.

    private String codificacionLetra (char letraClave,char letraMensaje)
    {
        char[] abecedario = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 
				'h', 'i', 'j', 'k','l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x','y', 'z'};
        String abecedarioString = new String(abecedario);
        int posicionClave=abecedarioString.indexOf(letraClave)+1;
        int posicionMensaje=abecedarioString.indexOf(letraMensaje)+1;
        int posicionLetraCifrada =(posicionClave+posicionMensaje)%26;
        
        char letraCodificada=abecedario[posicionLetraCifrada-1];
        char[] arrayChar = new char[1];
        arrayChar[0]=letraCodificada;
        String letracodificada= new String(arrayChar);

        return letracodificada;
    }
    //El metodo de decodificacion es igual al metodo de codificacion solo que llamamos al metodo de 
    //decodificacionLetra en vez de al metodo codificacionLetra. 
    public String decodificacion(String mensaje, String clave)
    {

        char [] mensajeCharArray = new char[mensaje.length()];
        clave=clave.toLowerCase();//convertimos la clave a minusculas si no lo esta
        mensaje=mensaje.toLowerCase();//convertimos el mensaje a minusculas si no lo esta
        mensajeCharArray=mensaje.toCharArray();//pasamos el mensaje a un chararray para poder trabajar mejor letra por letra.
        char letra=clave.charAt(0);//elegimos solo la primera letra de la clave en caso de que el usuario haya introducido mas letras
        String mensajecodificado="";
        //Para cada letra del mensaje codificaco la decodificamos para obtener el mensaje original.
        for(int i=0;i<mensaje.length();i++)
        {   
            mensajecodificado+=decodificacionLetra(letra,mensajeCharArray[i]);
        }
        
        return mensajecodificado;
    }
     //El metodo de decodificacion consiste en que nosotros obtendremos la letra de la clave y cada letra del mensaje
    //Este metodo es igual que el anterior solo que  en este caso no se hara el modulo de la suma de la posicion de la clave
    //y la posicion de la letra del mensaje sino que solo utilizaremos la posicion de la clave.
    private String decodificacionLetra (char letraClave,char letraMensaje)
    {
        char[] abecedario = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 
				'h', 'i', 'j', 'k','l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x','y', 'z'};
        String abecedarioString = new String(abecedario);
        int posicionClave=abecedarioString.indexOf(letraClave)+1;
        int posicionMensaje=abecedarioString.indexOf(letraMensaje)+1;
        int posicionLetraCifrada =posicionClave%26;
        posicionLetraCifrada=posicionMensaje-posicionLetraCifrada;
        
        char letraDecodificada=abecedario[posicionLetraCifrada-1];
        char[] arrayChar = new char[1];
        arrayChar[0]=letraDecodificada;
        String letracodificada= new String(arrayChar);

        return letracodificada;
    }
}
