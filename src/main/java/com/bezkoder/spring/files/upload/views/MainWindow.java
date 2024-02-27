package com.bezkoder.spring.files.upload.views;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.bezkoder.spring.files.upload.CloudingServerApplication;
import com.bezkoder.spring.files.upload.Configuracion;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class MainWindow  extends Application {

	public static Stage stage;
	public static Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Clouding Server Management");
		System.out.println("Ejecutando inicialización ventana");
		
		try {
			File r = new File("./src/main/java/com/bezkoder/spring/files/upload/views/fxml/mainwindow.fxml");
			Parent root = FXMLLoader.load(r.toURL());
            scene = new Scene(root);

            primaryStage.setScene(scene);
            
            Label txt_directorio = (Label) scene.lookup("#directorio");
            txt_directorio.setText(Configuracion.directorioCompleto);
            
            primaryStage.show();

       } catch(Exception e) {
            e.printStackTrace();
       } 
	}
	
	/**
	 * Añade una consulta y su resultado al listado para que se muestre en la pantalla
	 */
	public static void addConsultaListado(HBox consulta) {
		ListView<HBox> listaImagenes = (ListView<HBox>) scene.lookup("#lista");
        ObservableList<HBox> items = listaImagenes.getItems();
        items.add(consulta);
	}

}
