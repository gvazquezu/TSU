package com.crud_tareas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud_tareas.dto.ProyectoDto;
import com.crud_tareas.dto.ResponsableDto;
import com.crud_tareas.entity.Proyecto;
import com.crud_tareas.entity.Responsable;
import com.crud_tareas.repository.ProyectoRepository;

@Service
public class ProyectoService {

	@Autowired
	private ProyectoRepository proyectoRepository;
	
	//Consulta de todos los proyectos
	@Transactional(readOnly = true)
	public List<Proyecto> findAll() {
		return (List<Proyecto>)proyectoRepository.findAll();
	}

	//Consulta mediante ID
		@Transactional(readOnly = true)
		public Proyecto findById(Long id) {
			return (Proyecto) proyectoRepository.findById(id).orElse(null);
		}
	
	//Crear nuevo proyecto
	@Transactional
	public Proyecto createProyecto (ProyectoDto proyecto) {
		Proyecto proyectoEntity = new Proyecto();
		proyectoEntity.setNombre(proyecto.getNombre());
		proyectoEntity.setDescripcion(proyecto.getDescripcion());
		
		return proyectoRepository.save(proyectoEntity);
	}
	
	//Eliminar proyecto
	@Transactional
	public void delete(Long id) {
		proyectoRepository.deleteById(id);
	}
	
	// Actualizar proyecto existente
	@Transactional
	public Proyecto updateProyecto(Long id, ProyectoDto proyectoDto) {
	    Proyecto proyectoExistente = proyectoRepository.findById(id).orElse(null);

	    // Verificar proyecto existente
	    if (proyectoExistente != null) {
	        
	    	proyectoExistente.setNombre(proyectoDto.getNombre());
	    	proyectoExistente.setDescripcion(proyectoDto.getDescripcion());

	        return proyectoRepository.save(proyectoExistente);
	    }

	    return null;
	}
	
}
