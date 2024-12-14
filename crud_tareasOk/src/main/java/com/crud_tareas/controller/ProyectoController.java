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

import com.crud_tareas.dto.ProyectoDto;
import com.crud_tareas.entity.Proyecto;
import com.crud_tareas.service.ProyectoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

	@Autowired
	private ProyectoService proyectoService;
	
	
	//consulta de todos los proyectos
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	
	public List<Proyecto> consulta(){
	return proyectoService.findAll();
	}
	
	//consulta de proyecto por Id
	@GetMapping("/{id}")
	public ResponseEntity<?> consultaPorId(@PathVariable Long id) {
	    Proyecto proyecto = null;
	    String response = "";
	    try {
	    	proyecto = proyectoService.findById(id);
	    } catch (DataAccessException e) {
	        response = "Error al realizar la consulta: " + e.getMessage();
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    if (proyecto == null) {
	        response = "El proyecto con el id " .concat(id.toString()).concat(" no existe en la base de datos");
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<>(proyecto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Proyecto proyectoDelete = proyectoService.findById(id);
	        if (proyectoDelete == null) {
	            response.put("mensaje", "Error al eliminar. El proyecto no existe en la base de datos.");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	        
	        proyectoService.delete(id);
        
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al eliminar en la base de datos.");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    response.put("mensaje", "Proyecto eliminado con éxito.");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	// Crear proyecto
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ProyectoDto proyecto) {
		Proyecto proyectoNew = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			proyectoNew= this.proyectoService.createProyecto(proyecto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Proyecto creado con éxito con el id" + proyectoNew.getId() + ".");
		response.put("proyecto", proyectoNew);
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.CREATED);
	}
	
	//Actualizar proyecto por id
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProyectoDto proyecto) {
	    Proyecto proyectoActualizado = null;
	    Map<String, Object> response = new HashMap<>();

	    try {
	    	proyectoActualizado = proyectoService.updateProyecto(id, proyecto);
	        
	        if (proyectoActualizado == null) {
	            response.put("mensaje", "Error: No se pudo actualizar, el proyecto con el ID " + id + " no existe.");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la actualización en la base de datos.");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    response.put("mensaje", "Proyecto actualizado con éxito.");
	    response.put("proyecto", proyectoActualizado);
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}