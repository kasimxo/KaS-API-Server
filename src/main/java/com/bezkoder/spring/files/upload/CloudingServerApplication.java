package com.bezkoder.spring.files.upload;

// import javax.annotation.Resource; // for Spring Boot 2
import jakarta.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bezkoder.spring.files.upload.service.FilesStorageService;
import com.bezkoder.spring.files.upload.views.MainWindow;

@SpringBootApplication
public class CloudingServerApplication implements CommandLineRunner {
	
	public static Configuracion config;
	
	/*
  @Resource
  FilesStorageService storageService;
  */


  public static void main(String[] args) {
    SpringApplication.run(CloudingServerApplication.class, args);
  }

  @Override
  public void run(String... arg) throws Exception {
//    storageService.deleteAll();
	  
	  config = new Configuracion();
	  MainWindow.launch(MainWindow.class);
	  
	  //storageService.init();
  }
}
