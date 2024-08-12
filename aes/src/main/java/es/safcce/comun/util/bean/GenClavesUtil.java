package es.safcce.comun.util.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

import static com.google.common.base.Charsets.UTF_8;

public class GenClavesUtil {
	
	private GenClavesUtil() {
		throw new IllegalStateException("Clase de Utilidad");
	}

	public static String encode(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	public static byte[] decode(String input) {
		return Base64.decodeBase64(input.getBytes(UTF_8));
	}

	public static String getKeySecret() {
		SecureRandom sr = new SecureRandom();
		byte[] key = new byte[20];
		sr.nextBytes(key);
		return encode(key);
	}
	
	public static String toHex(byte[] array)
	    {
	        BigInteger bi = new BigInteger(1, array);
	        String hex = bi.toString(16);
	        int paddingLength = (array.length * 2) - hex.length();
	        if(paddingLength > 0)
	        {
	            return String.format("%0"  +paddingLength + "d", 0) + hex;
	        }else{
	            return hex;
	        }
	    }
	
	public static byte[] fromHex(String hex) {
	        byte[] bytes = new byte[hex.length() / 2];
	        for(int i = 0; i<bytes.length ;i++)
	        {
	            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	        }
	        return bytes;
	    }
	 
	  public static String generaHash(byte[] file, String algoritmo) {
	   
		  String hash = null;
		  
		  if (file.length != 0) {
		    //declarar funcion de resumen
		      try{
		      MessageDigest messageDigest = MessageDigest.getInstance(algoritmo); // Inicializa con algoritmo seleccionado
		     
		      //leer fichero byte a byte 
		         try{
		            InputStream archivo = new ByteArrayInputStream(file);
		            byte[] buffer = new byte[1];
		            int finArchivo = -1;
		            int caracter;
		       
		            caracter = archivo.read(buffer);
		       
		            while( caracter != finArchivo ) {
		         
		               messageDigest.update(buffer); // Pasa texto claro a la función resumen
		               caracter = archivo.read(buffer);
		            }   
		       
		            archivo.close();//cerramos el archivo
		            byte[] resumen = messageDigest.digest(); // Genera el resumen 
		            
		            //Pasar los resumenes a hexadecimal
		            hash = "";
		            for (int i = 0; i < resumen.length; i++)
		            {
		               hash += Integer.toHexString((resumen[i] >> 4) & 0xf);
		               hash += Integer.toHexString(resumen[i] & 0xf);
		            }
		          //  System.out.println("Resumen "+algoritmo+" " + hash);
		         }
		         //lectura de los datos del fichero
		         catch(java.io.FileNotFoundException fnfe) {
		         // TODO manejar excepcion archivo no encontrado
		         }
		         catch(java.io.IOException ioe) {
		         // TODO manejar excepcion archivo
		         }
		      
		      }   
		      //declarar funciones resumen
		      catch(java.security.NoSuchAlgorithmException nsae) {
		      //manejar excepcion algorito seleccionado erroneo
		      }
		  }
	        return hash;//regresamos el resumen
	      
	   }
	  
	 
}
