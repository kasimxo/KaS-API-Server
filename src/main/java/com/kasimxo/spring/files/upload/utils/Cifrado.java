package com.kasimxo.spring.files.upload.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.CipherOutputStream;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cifrado {
	
	private SecretKey secretKey;
	private Cipher cipher;
	
	/**
	 * Inicializamos un objeto de la clase cifrado que servira para encriptar y desencriptar
	 * 
	 * @param secretKey
	 * @param transformation
	 */
	public Cifrado(String transformation) {
		
	    try {
	    	this.secretKey = KeyGenerator.getInstance("AES").generateKey();
			this.cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Encripta la cadena al archivo especificado
	 * @param content
	 * @param fileName
	 */
	public void encrypt(String content, File archivoDestino) {
		try {
			
			System.out.println("Content\n"+content);
			
		    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		    //byte[] iv = cipher.getIV();
	
	
	    	FileOutputStream fileOut = new FileOutputStream(archivoDestino.getAbsoluteFile());
		    CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher);
	        //fileOut.write(iv);
	        cipherOut.write(content.getBytes());
	        
	        
	        
	        cipherOut.flush();
	        cipherOut.close();
	        fileOut.flush();
	        fileOut.close();
		        
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String decrypt(File archivoEntrada) {
	    String content;

	    try (FileInputStream fileIn = new FileInputStream(archivoEntrada.getAbsoluteFile())) {

	        cipher.init(Cipher.DECRYPT_MODE, secretKey);

	        try (
	                CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
	                InputStreamReader inputReader = new InputStreamReader(cipherIn);
	                BufferedReader reader = new BufferedReader(inputReader)
	            ) {

	            StringBuilder sb = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	            content = sb.toString();
	        }
	        System.out.println(content);
		    return content;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }

	}

}
