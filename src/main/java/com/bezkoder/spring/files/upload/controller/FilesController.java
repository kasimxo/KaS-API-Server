package com.bezkoder.spring.files.upload.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Base64;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bezkoder.spring.files.upload.CloudingServerApplication;
import com.bezkoder.spring.files.upload.Configuracion;
import com.bezkoder.spring.files.upload.message.ResponseMessage;
import com.bezkoder.spring.files.upload.model.FileInfo;
import com.bezkoder.spring.files.upload.service.FilesStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
public class FilesController {

  @Autowired
  FilesStorageService storageService;
  
  @PutMapping("/imagenes/{id}")
  public ResponseEntity<ResponseMessage> actualizarImagen(@PathVariable String id, @RequestParam("name") String filename) {
	  try {
		  System.out.println("Vamos a modificar el nombre");
		  File oldFile = new File(Configuracion.directorio+id);
		  File newFile = new File(Configuracion.directorio+filename);
		  Files.move(oldFile.toPath(), newFile.toPath());
		  return  ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("El archivo ha sido renombrado con éxito"));
	  } catch (Exception e) {
		  e.printStackTrace();
		  return null;
	  }
  }

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(
		  @RequestParam("fileName") String fileName,
		  @RequestParam("file") String file64) {

	  	System.out.println("UPLOADFILE REQUEST");
	 	String message = "";
	 	
	 	byte[] decodedBytes = Base64.getUrlDecoder().decode(file64);
	 	System.out.println(decodedBytes.length);
	 	
	 	File f = new File(Configuracion.directorio+fileName);
	 	
	 	try {
	 		f.createNewFile();
	 		
	 	FileOutputStream fos = new FileOutputStream(f);
	 	
	 	fos.write(decodedBytes);
	 	fos.flush();
	 	fos.close();

	 	System.out.println("Hemos guardado el archivo");

	 	message = "Se ha guardado el archivo " + fileName + " con éxito.";
	    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "No se ha podido guardar el archivo " + fileName + ". Error: " + e.getMessage();
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
    
  }
  
  @GetMapping("/imagenes")
  public ResponseEntity<List<String>> getAllImagenes() {
	  File[] archivosRaw = new File(Configuracion.directorio).listFiles();
	  List<String> archivos = new ArrayList<String>();
	  for(File f : archivosRaw) {
		  archivos.add(f.getName());
	  }

	  return ResponseEntity.status(HttpStatus.OK).body(archivos);
  }

/**
 * Recupera un archivo guardado en el sistema y lo envía al cliente
 * @param id
 * @return Devuelve una cadena de base64 con el archivo solicitado o mensaje de error si no lo encuentra
 */
  @GetMapping("/imagenes/{id}")
  public ResponseEntity<String> getImagen(@PathVariable String id) {
	
	System.out.println("Un usuario ha solicitado el archivo "+id);
	File selected = new File(CloudingServerApplication.config+id);
	  
	if(!selected.exists()) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	try {
		byte[] byteArray = Files.readAllBytes(Paths.get(selected.getAbsolutePath()));
		String encoded = Base64.getUrlEncoder().encodeToString(byteArray);
		return ResponseEntity.status(HttpStatus.OK).body(encoded);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
  }
  
  @DeleteMapping("/imagenes/{id}")
  public ResponseEntity<String> deleteImagen(@PathVariable String id) {
	  	System.out.println("Un usuario quiere borrar el archivo "+id);
		System.out.println("Se ha depurado a "+id);
		File selected = new File(Configuracion.directorio+id);
		System.out.println(selected.getAbsolutePath());
		if (selected.delete()) {
			System.out.println("Se ha eliminado la imagen");
			return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado la imagen con éxito");
		} else {
			System.out.println("No se ha encontrado la imagen");
			System.out.println(selected.getAbsolutePath());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} 
  }
  

  

}
