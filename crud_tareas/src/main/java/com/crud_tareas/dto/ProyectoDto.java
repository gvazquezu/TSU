package com.crud_tareas.dto;

import java.io.Serializable;

public class ProyectoDto implements Serializable {

	private String nombre;
	
	private String descripcion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
