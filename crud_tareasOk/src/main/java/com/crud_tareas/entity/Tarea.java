package com.crud_tareas.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tarea")
public class Tarea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name="nombreTarea")
	private String nombre;
	private String prioridad;
	private String estado;
	
	@Column (name="fecha_registro")
	private LocalDateTime createAt;
	
	@Column (name="fecha_cierre")
	private LocalDateTime createAtc;
	
	@ManyToOne (fetch = FetchType.LAZY) //Relación de muchas tareas a un responsable
	@JoinColumn(name = "responsable_id")
	@JsonManagedReference  // Se cambió a @JsonManagedReference para que Tarea incluya los datos del responsable
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Responsable responsable;
	
	public Responsable getResponsable() {
		return responsable;
	}
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}
	
	@ManyToOne (fetch = FetchType.LAZY) //Relación de muchas tareas a un proyecto
	@JoinColumn(name = "proyecto_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Proyecto proyecto;
	
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}	
	
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
	
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	
	public LocalDateTime getCreateAtc() {
		return createAtc;
	}
	public void setCreateAtc(LocalDateTime createAtc) {
		this.createAtc = createAtc;
	}

}
