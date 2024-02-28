package com.kasimxo.spring.files.upload;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kasimxo.spring.files.upload.utils.Cifrado;
import com.kasimxo.spring.files.upload.views.MainWindow;
import com.kasimxo.spring.files.upload.utils.Configuracion;

@SpringBootApplication
public class CloudingServerApplication implements CommandLineRunner {
	
	public static Configuracion config;
	public static Cifrado cifrado;
	


  public static void main(String[] args) {
    SpringApplication.run(CloudingServerApplication.class, args);
  }

  @Override
  public void run(String... arg) throws Exception {
	  cifrado = new Cifrado("nepe");
	  config = new Configuracion();
	  MainWindow.launch(MainWindow.class);
	  
  }
}
