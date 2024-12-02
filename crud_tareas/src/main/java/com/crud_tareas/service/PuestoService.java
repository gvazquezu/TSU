package com.crud_tareas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud_tareas.dto.PuestoDto;
import com.crud_tareas.entity.Puesto;
import com.crud_tareas.repository.PuestoRepository;

@Service
public class PuestoService {

	@Autowired
	private PuestoRepository puestoRepository;
	
	//Consulta de todos los puestos
	@Transactional(readOnly = true)
	public List<Puesto> findAll() {
		return (List<Puesto>)puestoRepository.findAll();
	}

	//Consulta por ID
		@Transactional(readOnly = true)
		public Puesto findById(Long id) {
			return (Puesto) puestoRepository.findById(id).orElse(null);
		}
	
	//Crear nuevo puesto
	@Transactional
	public Puesto createPuesto (PuestoDto puesto) {
		Puesto puestoEntity = new Puesto();
		puestoEntity.setNombre(puesto.getNombre());
		
		return puestoRepository.save(puestoEntity);
	}
	
	//Eliminar puesto
	@Transactional
	public void delete (Long id) {
		puestoRepository.deleteById(id);
	}
	
	// Actualizar puesto existente
	@Transactional
	public Puesto updatePuesto(Long id, PuestoDto puesto) {
		Puesto puestoExistente = puestoRepository.findById(id).orElse(null);

	    // Verificar puesto existente
	    if (puestoExistente != null) {
	        
	    	puestoExistente.setNombre(puesto.getNombre());

	        return puestoRepository.save(puestoExistente);
	    }

	    return null;
	}

}
