package com.kasimxo.spring.files.upload.views;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class VerHistorialWindow {

	public static Stage stage;
	public static Scene scene;
	
	public static void abrir(String historial) throws Exception {
		stage = new Stage();
		stage.setTitle("Historial");
		System.out.println("Ejecutando inicialización ventana ver historial");
		
		try {
			File r = new File("./src/main/java/com/kasimxo/spring/files/upload/views/fxml/verhistorialwindow.fxml");
			Parent root = FXMLLoader.load(r.toURL());
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
            ListView<String> lista = (ListView<String>) scene.lookup("#lista");
            ObservableList<String> lineas = lista.getItems();
            lineas.clear();
            
            String[] separado = historial.split(";");
            
            for (String linea : separado) {
            	lineas.add(linea);
            }
            
            stage.show();
       } catch(Exception e) {
            e.printStackTrace();
       } 
	}
	
}
