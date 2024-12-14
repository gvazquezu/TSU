package com.crud_tareas.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.crud_tareas.entity.Proyecto;
import com.crud_tareas.entity.Responsable;

import jakarta.persistence.Column;

public class TareaDto implements Serializable {

	 private String nombre;
	    private String prioridad;
	    private String estado;
	    private Long proyecto;
	    private Long responsable;
	    private LocalDateTime createAt;
	    private LocalDateTime createAtc;

	    // Getters y Setters
	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getPrioridad() { return prioridad; }
	    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

	    public String getEstado() { return estado; }
	    public void setEstado(String estado) { this.estado = estado; }

	    public Long getProyectoId() { return proyecto; }
	    public void setProyectoId(Long proyectoId) { this.proyecto = proyectoId; }

	    public Long getResponsableId() { return responsable; }
	    public void setResponsableId(Long responsableId) { this.responsable = responsableId; }

	    public LocalDateTime getCreateAt() { return createAt; }
	    public void setCreateAt(LocalDateTime createAt) { this.createAt = createAt; }

	    public LocalDateTime getCreateAtc() { return createAtc; }
	    public void setCreateAtc(LocalDateTime createAtc) { this.createAtc = createAtc; }
}
