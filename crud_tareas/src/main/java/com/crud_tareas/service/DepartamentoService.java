package com.crud_tareas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud_tareas.dto.DepartamentoDto;
import com.crud_tareas.entity.Departamento;
import com.crud_tareas.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	//Consulta de todos los departamentos
	@Transactional(readOnly = true)
	public List<Departamento> findAll() {
		return (List<Departamento>)departamentoRepository.findAll();
	}

	//Consulta por ID
		@Transactional(readOnly = true)
		public Departamento findById(Long id) {
			return (Departamento) departamentoRepository.findById(id).orElse(null);
		}
	
	//Crear nuevo departamento
	@Transactional
	public Departamento createDepartamento (DepartamentoDto departamento) {
		Departamento departamentoEntity = new Departamento();
		departamentoEntity.setNombre(departamento.getNombre());
		
		return departamentoRepository.save(departamentoEntity);
	}
	
	//Eliminar departamento
	@Transactional
	public void delete(Long id) {
		departamentoRepository.deleteById(id);
	}
	
	// Actualizar responsable existente
	@Transactional
	public Departamento updateDepartamento(Long id, DepartamentoDto departamento) {
		Departamento departamentoExistente = departamentoRepository.findById(id).orElse(null);

	    // Verificar departamento existente
	    if (departamentoExistente != null) {
	        
	    	departamentoExistente.setNombre(departamento.getNombre());

	        return departamentoRepository.save(departamentoExistente);
	    }

	    return null;
	}

}
