package com.crud_tareas.controller;

import java.util.List;

import java.util.Map;
import java.time.LocalDateTime;
import java.util.HashMap;

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

import com.crud_tareas.dto.TareaDto;
import com.crud_tareas.entity.Proyecto;
import com.crud_tareas.entity.Responsable;
import com.crud_tareas.entity.Tarea;
import com.crud_tareas.service.TareaService;
import com.crud_tareas.service.ProyectoService;
import com.crud_tareas.service.ResponsableService;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/tareas")
public class TareaController {


	@Autowired
	private TareaService tareaService;
	
	  @Autowired
	    private ProyectoService proyectoService;

	    @Autowired
	    private ResponsableService responsableService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	
	public List<Tarea> consulta(){
	return tareaService.findAllWithResponsable();
	}
	
	//Realizar busqueda por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {
		
		Tarea tarea=null;
		String response="";
		try {
			tarea = tareaService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(tarea== null) 
		{
			response = "La tarea con el ID".concat(id.toString()).concat("no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	//Eliminar la tarea con el ID especificado
	
	@DeleteMapping("/tareas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Tarea tareaDelete = this.tareaService.findById(id);
			
			if(tareaDelete == null)
			{
				response.put("mensaje", "Error al eliminar. la tarea no existe en la base de datos.");
				return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			tareaService.delete(id);
			response.put("mensaje", "Tarea eliminada con éxito.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// Crear tarea
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody TareaDto tarea) {
		Map<String, Object> response = new HashMap<>();
        try {
            if (tarea.getCreateAtc() == null) {
                tarea.setCreateAtc(LocalDateTime.now().plusDays(7)); // Fecha de cierre por defecto
            }
            Tarea nuevaTarea = tareaService.createTarea(tarea);
            response.put("mensaje", "Tarea agregada con éxito, con el ID " + nuevaTarea.getId());
            response.put("tarea", nuevaTarea);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	//Actualizar tarea por id
	@PutMapping("/tareas/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TareaDto tarea) {
		 Map<String, Object> response = new HashMap<>();
	        
		 try {
	            Tarea tareaActual = tareaService.findById(id);
	            if (tareaActual == null) {
	                response.put("mensaje", "Error: no se pudo editar, la tarea con ID ".concat(id.toString()).concat(" no existe en la base de datos."));
	                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	            }

	            // Actualizamos los campos de la tarea actual
	            if (tarea.getNombre() != null) tareaActual.setNombre(tarea.getNombre());
	            if (tarea.getPrioridad() != null) tareaActual.setPrioridad(tarea.getPrioridad());
	            if (tarea.getEstado() != null) tareaActual.setEstado(tarea.getEstado());

	            // Asignamos el Proyecto basándonos en su ID
	            if (tarea.getProyectoId() != null) {
	                Proyecto proyecto = proyectoService.findById(tarea.getProyectoId());
	                if (proyecto != null) {
	                    tareaActual.setProyecto(proyecto);
	                } else {
	                    response.put("mensaje", "Error: el proyecto con ID ".concat(tarea.getProyectoId().toString()).concat(" no existe."));
	                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	                }
	            }

	            // Asignamos el Responsable basándonos en su ID
	            if (tarea.getResponsableId() != null) {
	                Responsable responsable = responsableService.findById(tarea.getResponsableId());
	                if (responsable != null) {
	                    tareaActual.setResponsable(responsable);
	                } else {
	                    response.put("mensaje", "Error: el responsable con ID ".concat(tarea.getResponsableId().toString()).concat(" no existe."));
	                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	                }
	            }

	            // Guardamos la tarea actualizada
	            Tarea tareaUpdated = tareaService.updateTarea(tareaActual);
	            response.put("mensaje", "La tarea ha sido actualizada con éxito");
	            response.put("tarea", tareaUpdated);
	            return new ResponseEntity<>(response, HttpStatus.OK);

	        } catch (DataAccessException e) {
	            response.put("mensaje", "Error al actualizar la tarea en la base de datos");
	            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }


}
