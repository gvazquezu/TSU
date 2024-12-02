package com.crud_tareas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crud_tareas.dto.DepartamentoDto;
import com.crud_tareas.entity.Departamento;
import com.crud_tareas.service.DepartamentoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/departamento")
public class DepartamentoController {

	@Autowired
	private DepartamentoService departamentoService;
	
	//Obtener a todos los departamentos
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	
	public List<Departamento> consulta(){
		return departamentoService.findAll();
	}
	
	//Obtener departamento por id
	@GetMapping("/{id}")
	public ResponseEntity<?> consultaPorId(@PathVariable Long id) {
		Departamento departamento = null;
	    String response = "";
	    
	    try {
	    	departamento = departamentoService.findById(id);
	    } catch (DataAccessException e) {
	        response = "Error al realizar la consulta: " + e.getMessage();
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    if (departamento == null) {
	        response = "El departamento con el id ".concat(id.toString()).concat(" no existe en la base de datos");
	        return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
	}

	//Eliminar departamento por id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	    	Departamento departamentoDelete = this.departamentoService.findById(id);
	        if (departamentoDelete == null) {
	            response.put("mensaje", "Error al eliminar. El departamento no existe en la base de datos.");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	        }
	        
	        departamentoService.delete(id);
	        
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al eliminar en la base de datos.");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    response.put("mensaje", "Departamento eliminado con éxito.");
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	
	// Crear departamento
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody DepartamentoDto departamento) {
		Departamento departamentoNew = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			departamentoNew= this.departamentoService.createDepartamento(departamento);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Departamento creado con éxito con el nombre" + departamentoNew.getId() + ".");
		response.put("departamento", departamentoNew);
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.CREATED);
	}
	
	//Actualizar departamento por nombre
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DepartamentoDto departamento) {
		Departamento departamentoActualizado = null;
	    Map<String, Object> response = new HashMap<>();

	    try {
	    	departamentoActualizado = departamentoService.updateDepartamento(id, departamento);
	        
	        if (departamentoActualizado == null) {
	            response.put("mensaje", "Error: No se pudo actualizar, el departamento con el ID " + id + " no existe.");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la actualización en la base de datos.");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    response.put("mensaje", "Departamento actualizado con éxito.");
	    response.put("departamento", departamentoActualizado);
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
