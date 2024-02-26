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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bezkoder.spring.files.upload.message.ResponseMessage;
import com.bezkoder.spring.files.upload.model.FileInfo;
import com.bezkoder.spring.files.upload.service.FilesStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
public class FilesController {

  @Autowired
  FilesStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(
		  @RequestParam("fileName") String fileName,
		  @RequestParam("file") String file64) {
	  	//@RequestParam("file")MultipartFile file
	  	System.out.println(file64);
	  	System.out.println("UPLOADFILE REQUEST");
	 	String message = "";
	 	
	 	byte[] decodedBytes = Base64.getUrlDecoder().decode(file64);
	 	System.out.println(decodedBytes.length);
	 	File f = new File("./src/main/resources/imagenes/"+fileName);
	 	
	 	try {
	 		f.createNewFile();
	 		System.out.println("Vamos a iniciar fos");
	 		
	 	FileOutputStream fos = new FileOutputStream(f);
	 	
	 	System.out.println("Hemos iniciado fos");
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
	  System.out.println("asfldsajh");
	  File[] archivosRaw = new File("./src/main/resources/imagenes").listFiles();
	  List<String> archivos = new ArrayList<String>();
	  for(File f : archivosRaw) {
		  archivos.add(f.getName());
		  System.out.println(f.getName());
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
	  
	File selected = new File("./src/main/resources/imagenes/"+id);
	  
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
  
		File selected = new File("./src/main/resources/imagenes/"+id);
		if(!selected.exists()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else if (selected.delete()) {
			return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado la imagen con éxito");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha producido algún error");
	  
  }
  

  

}
