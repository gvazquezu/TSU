package com.crud_tareas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud_tareas.dto.ResponsableDto;
import com.crud_tareas.entity.Departamento;
import com.crud_tareas.entity.Puesto;
import com.crud_tareas.entity.Responsable;
import com.crud_tareas.repository.ResponsableRepository;

@Service
public class ResponsableService {

    @Autowired
    private ResponsableRepository responsableRepository;

    @Autowired
    private PuestoService puestoService;

    @Autowired
    private DepartamentoService departamentoService;

    //Consulta de todos los responsables
    @Transactional(readOnly = true)
    public List<Responsable> findAll() {
        return (List<Responsable>) responsableRepository.findAll();
    }

    //Consulta por ID
    @Transactional(readOnly = true)
    public Responsable findById(Long id) {
        return responsableRepository.findById(id).orElse(null);
    }

 // Crear nuevo responsable
    @Transactional
    public Responsable createResponsable(ResponsableDto responsableDto) {
        Responsable responsableEntity = new Responsable();
        responsableEntity.setNombre(responsableDto.getNombre());
        responsableEntity.setApellido(responsableDto.getApellido());
        responsableEntity.setCorreo(responsableDto.getCorreo());
        responsableEntity.setCelular(responsableDto.getCelular());

        // Obtener el departamento y el puesto a partir de sus IDs
        if (responsableDto.getDepartamento() != null) {
            Departamento departamento = departamentoService.findById(responsableDto.getDepartamento().getId());
            responsableEntity.setDepartamento(departamento); 
        }

        if (responsableDto.getPuesto() != null) {
            Puesto puesto = puestoService.findById(responsableDto.getPuesto().getId());
            responsableEntity.setPuesto(puesto); 
        }

        return responsableRepository.save(responsableEntity); // Guardar el responsable en la base de datos
    }


    //Eliminar responsable
    @Transactional
    public void delete(Long id) {
        responsableRepository.deleteById(id);
    }

    // Actualizar responsable existente
    @Transactional
    public Responsable updateResponsable(Responsable responsable) {
        return responsableRepository.save(responsable);
    }

    // Métodos para obtener departamento y puesto por ID (en caso de que sea necesario)
    public Departamento findDepartamentoById(Long id) {
        return departamentoService.findById(id);
    }

    public Puesto findPuestoById(Long id) {
        return puestoService.findById(id);
    }
}