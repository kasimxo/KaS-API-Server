package com.kasimxo.spring.files.upload.controller;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.kasimxo.spring.files.upload.utils.Configuracion;
import com.kasimxo.spring.files.upload.message.ResponseMessage;
import com.kasimxo.spring.files.upload.model.DisplayRespuesta;
import com.kasimxo.spring.files.upload.views.MainWindow;

import javafx.application.Platform;

@Controller
@CrossOrigin("http://localhost:8081")
public class FilesController {


	@PutMapping("/imagenes/{id}")
	public ResponseEntity<ResponseMessage> actualizarImagen(@PathVariable String id,
			@RequestParam("name") String filename) {
		try {
			System.out.println("Vamos a modificar el nombre");
			File oldFile = new File(Configuracion.directorio + "/" + id);
			File newFile = new File(Configuracion.directorio + "/" + filename);
			Files.move(oldFile.toPath(), newFile.toPath());
			ResponseEntity<ResponseMessage> respuesta = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage("El archivo ha sido renombrado con éxito"));

			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("PUT","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<ResponseMessage> respuesta = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ResponseMessage("Fallo renombrando el archivo"));
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("PUT","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		}
	}

	/**
	 * Guarda un archivo del usuario.
	 * Solicita también el nombre con el que se va guardar el archivo
	 * @param fileName
	 * @param file
	 * @return
	 */
	@PostMapping("/subir")
	public ResponseEntity<ResponseMessage> uploadFile(
			@RequestHeader("filename") String fileName,
			@RequestParam("file") MultipartFile file) {

		String message = "";
		try {
			InputStream is =  file.getInputStream();
			
			File f = new File(Configuracion.directorio+"/"+fileName);
			f.createNewFile();
			System.out.println("Se ha creado el nuevo archivo en " + f.getAbsolutePath());
			
			FileOutputStream fos = new FileOutputStream(f);
			
			byte[] buffer = new byte[8 * 1024];
		    int bytesRead;
		    while ((bytesRead = is.read(buffer)) != -1) {
		        fos.write(buffer, 0, bytesRead);
		    }
		    
		    fos.flush();
		    fos.close();
		    is.close();

			System.out.println("Hemos guardado el archivo");

			message = "Se ha guardado el archivo " + fileName + " con éxito.";

			ResponseEntity<ResponseMessage> respuesta = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(message));
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("POST","http://localhost:8081/upload",respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		} catch (Exception e) {
			message = "No se ha podido guardar el archivo " + fileName + ". Error: " + e.getMessage();
			e.printStackTrace();

			ResponseEntity<ResponseMessage> respuesta = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage(message));
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("POST","http://localhost:8081/upload",respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		}

	}

	@GetMapping("/imagenes")
	public ResponseEntity<List<String>> getAllImagenes() {
		File[] archivosRaw = new File(Configuracion.directorio).listFiles();
		List<String> archivos = new ArrayList<String>();
		for (File f : archivosRaw) {
			archivos.add(f.getName());
		}
		ResponseEntity<List<String>> respuesta = ResponseEntity.status(HttpStatus.OK).body(archivos);
		DisplayRespuesta dr = new DisplayRespuesta("GET","http://localhost:8081/imagenes",respuesta.getStatusCode().value());
		Platform.runLater(() -> MainWindow.addConsultaListado(dr.toHbox()));
		return respuesta;
	}

	/**
	 * Recupera un archivo guardado en el sistema y lo envía al cliente
	 * 
	 * @param id
	 * @return Devuelve una cadena de base64 con el archivo solicitado o mensaje de
	 *         error si no lo encuentra
	 */
	@GetMapping("/imagenes/{id}")
	public ResponseEntity<String> getImagen(@PathVariable String id) {

		System.out.println("Un usuario ha solicitado el archivo " + id);
		File selected = new File(Configuracion.directorio + "/" + id);
		System.out.println(selected.getAbsolutePath());
		if (!selected.exists()) {
			ResponseEntity<String> respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("GET","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		}
		try {
			byte[] byteArray = Files.readAllBytes(Paths.get(selected.getAbsolutePath()));
			String encoded = Base64.getUrlEncoder().encodeToString(byteArray);
			ResponseEntity<String> respuesta = ResponseEntity.status(HttpStatus.OK).body(encoded);
			
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("GET","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			
			return respuesta;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResponseEntity<String> respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("GET","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		}
	}

	@DeleteMapping("/imagenes/{id}")
	public ResponseEntity<String> deleteImagen(@PathVariable String id) {
		System.out.println("Un usuario quiere borrar el archivo " + id);
		System.out.println("Se ha depurado a " + id);
		File selected = new File(Configuracion.directorio + "/" + id);
		System.out.println(selected.getAbsolutePath());
		if (selected.delete()) {
			System.out.println("Se ha eliminado la imagen");
			ResponseEntity<String> respuesta = ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado la imagen con éxito");
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("DELETE","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		} else {
			System.out.println("No se ha encontrado la imagen");
			System.out.println(selected.getAbsolutePath());
			ResponseEntity<String> respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			Platform.runLater(() -> MainWindow.addConsultaListado(new DisplayRespuesta("DELETE","http://localhost:8081/imagenes/"+id,respuesta.getStatusCode().value()).toHbox()));
			return respuesta;
		}
	}

}
