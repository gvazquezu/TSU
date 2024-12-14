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

import com.crud_tareas.dto.PuestoDto;
import com.crud_tareas.entity.Puesto;
import com.crud_tareas.service.PuestoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/puesto")
public class PuestoController {

	@Autowired
	private PuestoService puestoService;
	
	
	//Busqueda de todos los puestos
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	
	public List<Puesto> consulta(){
	return puestoService.findAll();
	}
	
	
	//Busqueda de los puestos por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> consultaPorId(@PathVariable Long id) {
		Puesto puesto = null;
	    String response = "";
	    try {
	    	puesto = puestoService.findById(id);
	    } catch (DataAccessException e) {
	        response = "Error al realizar la consulta: " + e.getMessage();
	        return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    if (puesto == null) {
	        response = "El puesto con el id ".concat(id.toString()).concat(" no existe en la base de datos");
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<Puesto>(puesto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	    	Puesto puestoDelete = puestoService.findById(id);
	        if (puestoDelete == null)
	        {
	            response.put("mensaje", "Error al eliminar. El puesto no existe en la base de datos.");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	        
	        puestoService.delete(id);
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al eliminar en la base de datos.");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    response.put("mensaje", "Puesto eliminado con éxito.");
	    return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}

	
	// Crear puesto
	@PostMapping("/puesto")
	public ResponseEntity<?> create(@RequestBody PuestoDto puesto) {
		Puesto puestoNew = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			puestoNew= this.puestoService.createPuesto(puesto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Puesto creado con éxito con el nombre" + puestoNew.getNombre() + ".");
		response.put("puesto", puestoNew);
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PuestoDto puestoDto) {
	    Puesto puestoActualizado = null;
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // Buscar el puesto existente por ID
	        Puesto puestoExistente = puestoService.findById(id);

	        if (puestoExistente == null) {
	            response.put("mensaje", "Error: No se pudo actualizar, el puesto con el ID " + id + " no existe.");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	        }

	        // Actualizar los campos del puesto existente con los valores del DTO
	        puestoExistente.setNombre(puestoDto.getNombre());

	        // Guardar los cambios en la base de datos
	        puestoActualizado = puestoService.save(puestoExistente);

	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la actualización en la base de datos.");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    response.put("mensaje", "Puesto actualizado con éxito.");
	    response.put("puesto", puestoActualizado);
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}



}