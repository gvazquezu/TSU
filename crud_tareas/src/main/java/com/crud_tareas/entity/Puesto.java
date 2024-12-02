package com.crud_tareas.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "puesto")
public class Puesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name="nombrePuesto")
	private String nombre;

    @OneToOne(mappedBy = "puesto", fetch =FetchType.EAGER) // Relación de un responsable a un puesto
    @JsonIgnoreProperties({"puesto"}) // Ignora la propiedad "puesto" de Responsable para evitar el bucle
    @JsonIgnore 
    private Responsable responsable;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Responsable getResponsable() {
		return responsable;
	}
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

}
