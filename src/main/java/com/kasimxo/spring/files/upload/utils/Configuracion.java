package com.kasimxo.spring.files.upload.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;

public class Configuracion {
	
	//Directorio que se usará para el almacenamiento de archivos durante la ejecución del programa
	public static String directorio;
	
	public static String directorioCompleto;
	
	public static File archivoConfig;

	private static Map<String, String> rutas;
	
	public Configuracion() {
		rutas = new HashMap<String, String>();

		archivoConfig = new File("./src/main/resources/config.txt"); 
		
		try {
			
			if(!archivoConfig.exists()) {
				archivoConfig.createNewFile();
				actualizarArchivoConfig();
			}
			
			for (String linea : Files.readAllLines(archivoConfig.toPath())) {
				String[] separado = linea.split("=");
				
				rutas.put(separado[0], separado[1]);
			}
		} catch (IOException e) {
			
		}
		//Aqui ponemos cada ruta
		rutas.forEach((K, V) -> {
			switch (K) {
			case "directorio":
				directorio = V;
				break;
			default:
				break;
			}
		});
		directorioCompleto = new File(directorio).getAbsolutePath();
	}
	
	public static void actualizarArchivoConfig() {
		
		try {
			FileWriter fw = new FileWriter(archivoConfig);
			
			rutas.forEach((K, V) -> {
				String linea = String.format("%s=%s\n", K, V);
				try {
					fw.write(linea, 0, linea.length()); 
					fw.flush(); 
				} catch (Exception e) {
				}
			});

			fw.close(); 
			
			directorioCompleto = new File(directorio).getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public static void actualizarRutas() {
		rutas.put("directorio", directorio);
		actualizarArchivoConfig();
	}
	

	
	public static void actualizarDirectorio(String newDirectorio) {
		directorio = newDirectorio;
		actualizarRutas();
	}
	
	/**
	 * Restaura la configuración predeterminada <br>
	 * IP: localhost<br>
	 * Puerto: 8081
	 */
	public static void restaurarPredeterminado() {
		directorio = "./src/main/resources/imagenes/";
		actualizarRutas();
	}
	
	
}
