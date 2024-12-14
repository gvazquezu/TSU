package com.crud_tareas.dto;

import java.io.Serializable;

public class PuestoDto implements Serializable {

private Long id;
	
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
