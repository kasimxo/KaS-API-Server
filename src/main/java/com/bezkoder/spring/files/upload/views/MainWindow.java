package com.bezkoder.spring.files.upload.views;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            primaryStage.show();

       } catch(Exception e) {
            e.printStackTrace();
       } 
	}

}
