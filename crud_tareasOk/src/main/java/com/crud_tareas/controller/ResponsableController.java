package com.crud_tareas.controller;

import java.util.List;
import java.util.Map;
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

import com.crud_tareas.dto.ResponsableDto;
import com.crud_tareas.entity.Departamento;
import com.crud_tareas.entity.Puesto;
import com.crud_tareas.entity.Responsable;
import com.crud_tareas.service.ResponsableService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/responsable")
public class ResponsableController {

	@Autowired
	private ResponsableService responsableService;
	
	@Autowired
	private ResponsableService puestoService;
	
	@Autowired
	private ResponsableService departamentoService;
	
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public List<Responsable> consulta() {
		return responsableService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> consultaPorId(@PathVariable Long id) {
		Responsable responsable = null;
	    String response = "";
	    try {
	        responsable = responsableService.findById(id);
	        if (responsable == null) {
		    	response = "El responsable con el ID".concat(id.toString()).concat(" no existe en la base de datos.");
		        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		    }
	    } catch (DataAccessException e) {
	        response = "Error al realizar la consulta: " + e.getMessage();
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    return new ResponseEntity<>(responsable, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Responsable responsableDelete = responsableService.findById(id);
	        if (responsableDelete == null) {
	            response.put("mensaje", "Error al eliminar. El responsable no existe en la base de datos.");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	        responsableService.delete(id);
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al eliminar en la base de datos.");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    response.put("mensaje", "Responsable eliminado con éxito.");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
    public ResponseEntity<?> createResponsable(@RequestBody ResponsableDto responsableDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validaciones de campos obligatorios
            if (responsableDto.getNombre() == null || responsableDto.getNombre().isEmpty()) {
                response.put("mensaje", "El nombre del responsable es obligatorio");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (responsableDto.getApellido() == null || responsableDto.getApellido().isEmpty()) {
                response.put("mensaje", "El apellido del responsable es obligatorio");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Crear responsable
            Responsable nuevoResponsable = responsableService.createResponsable(responsableDto);
            response.put("mensaje", "Responsable creado con éxito");
            response.put("responsable", nuevoResponsable);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("mensaje", "Error inesperado al crear el responsable");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
	// Actualizar responsable por id
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ResponsableDto responsable) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Responsable responsableActualizado = responsableService.findById(id);
			if (responsableActualizado == null) {
				response.put("mensaje", "Error: no se pudo editar, el responsable con ID " + id + " no existe en la base de datos.");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			// Actualizar campos no nulos
			if (responsable.getNombre() != null) {
				responsableActualizado.setNombre(responsable.getNombre());
			}
			if (responsable.getApellido() != null) {
				responsableActualizado.setApellido(responsable.getApellido());
			}
			if (responsable.getCorreo() != null) {
				responsableActualizado.setCorreo(responsable.getCorreo());
			}
			if (responsable.getCelular() != null) {
				responsableActualizado.setCelular(responsable.getCelular());
			}

			// Validar y asignar departamento
			if (responsable.getDepartamento() != null) {
				Departamento departamento = responsableService.findDepartamentoById(responsable.getDepartamento().getId());
				if (departamento == null) {
					response.put("mensaje", "El departamento con ID " + responsable.getDepartamento().getId() + " no existe.");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				responsableActualizado.setDepartamento(departamento);
			}

			// Validar y asignar puesto
			if (responsable.getPuesto() != null) {
				Puesto puesto = responsableService.findPuestoById(responsable.getPuesto().getId());
				if (puesto == null) {
					response.put("mensaje", "El puesto con ID " + responsable.getPuesto().getId() + " no existe.");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				responsableActualizado.setPuesto(puesto);
			}

			// Guardar cambios
			Responsable responsableUpdated = responsableService.updateResponsable(responsableActualizado);
			response.put("mensaje", "El responsable ha sido actualizado con éxito.");
			response.put("responsable", responsableUpdated);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el responsable en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}