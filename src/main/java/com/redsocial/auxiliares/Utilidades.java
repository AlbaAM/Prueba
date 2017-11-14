package com.redsocial.auxiliares;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
 
/**
 * 
 * @author Usuario
 *
 */
public class Utilidades {
	
	private final static String utf8 = "utf-8";
	private final static String desede = "DESede";
 
    public static String Encriptar(String texto) {
 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digestOfPassword = md.digest(secretKey.getBytes(utf8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, desede);
            Cipher cipher = Cipher.getInstance(desede);
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes(utf8);
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
 
    public static String Desencriptar(String textoEncriptado) throws Exception {
 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes(utf8));
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digestOfPassword = md.digest(secretKey.getBytes(utf8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, desede);
 
            Cipher decipher = Cipher.getInstance(desede);
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
}