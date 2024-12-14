package com.crud_tareas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.crud_tareas.entity.Tarea;

@Repository
public interface ITareaRepository extends JpaRepository <Tarea, Long>{
	@Query("SELECT t FROM Tarea t JOIN FETCH t.responsable")
	List<Tarea> findAllWithResponsable();
}
