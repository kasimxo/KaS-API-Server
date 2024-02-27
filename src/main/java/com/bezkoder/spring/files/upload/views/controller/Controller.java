package com.bezkoder.spring.files.upload.views.controller;

import java.io.File;

import com.bezkoder.spring.files.upload.Configuracion;
import com.bezkoder.spring.files.upload.views.MainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Controller {

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
	
}
