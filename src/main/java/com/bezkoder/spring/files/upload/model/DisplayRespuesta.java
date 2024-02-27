package com.bezkoder.spring.files.upload.model;

import java.util.Date;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;

public class DisplayRespuesta {
	
	private String tipoPeticion;
	private String resultadoPeticion;
	private Date fecha;
	private String uri;
	
	/**
	 * 
	 * @param tipo -> GET, POST, PUT, DELETE
	 * @param direccion -> URI
	 * @param codigo -> Resultado de la petición
	 */
	public DisplayRespuesta(String tipo, String direccion, String codigo) {
		tipoPeticion = tipo;
		resultadoPeticion = codigo;
		uri = direccion;
		fecha = new Date();  
	}
	
	public DisplayRespuesta(String tipo, String direccion, int codigo) {
		tipoPeticion = tipo;
		resultadoPeticion = Integer.toString(codigo);
		uri = direccion;
		fecha = new Date();  
	}
	
	public String getTipoPeticion() {
		return tipoPeticion;
	}
	public void setTipoPeticion(String tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}
	public String getResultadoPeticion() {
		return resultadoPeticion;
	}
	public void setResultadoPeticion(String resultadoPeticion) {
		this.resultadoPeticion = resultadoPeticion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public HBox toHbox() {
		HBox linea = new HBox();
		
		Label tipo = new Label(tipoPeticion);
		linea.getChildren().add(tipo);
		
		Region espacio1 = new Region();
    	HBox.setHgrow(espacio1, Priority.ALWAYS);
    	linea.getChildren().add(espacio1);
    	
		Label direccion = new Label(uri);
		linea.getChildren().add(direccion);
		
		Region espacio2 = new Region();
    	HBox.setHgrow(espacio2, Priority.ALWAYS);
    	linea.getChildren().add(espacio2);
    	
		Label cod = new Label(resultadoPeticion);
		linea.getChildren().add(cod);
		
		Region espacio3 = new Region();
    	HBox.setHgrow(espacio3, Priority.ALWAYS);
    	linea.getChildren().add(espacio3);
		
		Label hora = new Label(fecha.toString());
		linea.getChildren().add(hora);
		
		return linea;
	}

}
