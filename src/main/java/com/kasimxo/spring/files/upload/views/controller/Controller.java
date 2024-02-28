package com.kasimxo.spring.files.upload.views.controller;

import java.io.File;

import com.kasimxo.spring.files.upload.CloudingServerApplication;
import com.kasimxo.spring.files.upload.utils.Configuracion;
import com.kasimxo.spring.files.upload.views.MainWindow;
import com.kasimxo.spring.files.upload.views.VerHistorialWindow;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.Node;

import java.util.Date;

public class Controller {
	
	private String buffer;

	@FXML
	public void modificarDirectorio(ActionEvent event) {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
		 	chooser.setTitle("Selecciona el directorio");

		 	File selectedDirectory = chooser.showDialog(MainWindow.stage);
		 	
		 	Configuracion.actualizarDirectorio(selectedDirectory.getAbsolutePath());
		 	Label txt_directorio = (Label) MainWindow.scene.lookup("#directorio");
            txt_directorio.setText(Configuracion.directorioCompleto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void exportarHistorial(ActionEvent event) {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
		 	chooser.setTitle("Selecciona el directorio en el que exportar el archivo");

		 	File selectedDirectory = chooser.showDialog(MainWindow.stage);
		 	
		 	ListView<HBox> listaImagenes = (ListView<HBox>) MainWindow.scene.lookup("#lista");
		 	
		 	ObservableList<HBox> items = listaImagenes.getItems();
		 	
		 	buffer = "";
		 	
		 	items.forEach((box) -> {
		 		ObservableList<Node> nodos = box.getChildren();
		 		
		 		nodos.forEach((n) -> {
			 		
			 		if(n instanceof Label) {
			 			buffer += ((Label) n).getText() + "\t";
			 		}
		 		});
		 		
		 		buffer += ";";
		 	});
		 	
		 	String fileName = "Historial "+new Date().toString();
		 	fileName.replace(' ', '_');
		 	
		 	File archivoDestino = new File(selectedDirectory.getAbsolutePath()+"/"+fileName);
		 	CloudingServerApplication.cifrado.encrypt(buffer, archivoDestino);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void abrirHistorial() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Selección de archivo");
		File selected = fc.showOpenDialog(MainWindow.stage);
		
		ListView<HBox> listaImagenes = (ListView<HBox>) MainWindow.scene.lookup("#lista");
	 	
	 	ObservableList<HBox> items = listaImagenes.getItems();
	 	items.clear();
	 	
	 	String contenido = CloudingServerApplication.cifrado.decrypt(selected);
	 	
	 	try {
			VerHistorialWindow.abrir(contenido);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	
	 	System.out.println(contenido);
		
	}
	
}
